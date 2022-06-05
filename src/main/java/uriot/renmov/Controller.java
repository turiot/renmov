package uriot.renmov;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.io.FileUtils;

public class Controller {

	public Logger log;
	public Settings settings;
	public ResourceBundle bundle;

	void search(ArrayList<Item> items, String dir1, String dir2, int[] res) {
		items.clear();
		
		if (!new File(dir1).exists() || !new File(dir2).exists()) {
			res[0] = 1;
			return;
		}
		
		var map = new ArrayListValuedHashMap<Criteria, Item>();
		try (var walk = Files.walk(Path.of(new URI("file:///" + dir2)))) {
			walk.filter(Files::isRegularFile).map(path -> {
				var file = path.toFile();
				Criteria key = null;
				try {
					var date = ((FileTime) Files.getAttribute(path, "creationTime")).toInstant();
					key = new Criteria(date, file.length(), settings.roundToSecond);
				}
				catch (IOException ex) {
					log.log(Level.SEVERE, null, ex);
					res[0] = 2;
				}
				var item = new Item(key);
				item.path2 = file.getPath();
				return item;
			})
			.forEach(item -> {
				map.put(item.criteria, item);
			});
		}
		catch (URISyntaxException | IOException ex) {
			log.log(Level.SEVERE, null, ex);
			res[0] = 2;
		}
		
		try (var walk = Files.walk(Path.of(new URI("file:///" + dir1)))) {
			walk.filter(Files::isRegularFile)
				.<Item>mapMulti((path, consumer) -> {
					var file1 = path.toFile();
					Criteria key = null;
					try {
						var date = ((FileTime) Files.getAttribute(path, "creationTime")).toInstant();
						key = new Criteria(date, file1.length(), settings.roundToSecond);
					}
					catch (IOException ex) {
						log.log(Level.SEVERE, null, ex);
						res[0] = 2;
					}
					var found = map.get(key);
					if (found != null && !found.isEmpty()) {
						found.forEach(item -> {
							var relPath1 = file1.getPath().substring(dir1.length() + 1);
							var relPath2 = item.path2.substring(dir2.length() + 1);
							if (!relPath1.equals(relPath2)) {
								var file2 = new File(item.path2);
								var d1 = relPath1.substring(0, relPath1.lastIndexOf(file1.getName()));
								var d2 = relPath2.substring(0, relPath2.lastIndexOf(file2.getName()));
								var out = new Item(item.criteria);
								out.select = found.size() <= 1;
								if (d1.equals(d2)) {
									out.action = bundle.getString("action.rename");
								}
								else if (file1.getName().equals(file2.getName())) {
									out.action = bundle.getString("action.move");
								}
								else {
									out.action = bundle.getString("action.both");
								}
								out.path1 = file1.getPath();
								out.path2 = item.path2;
								consumer.accept(out);
							}
						});
					}
				})
				.filter(item -> item != null)
				.forEach(item -> {
					items.add(item);
				});
		}
		catch (URISyntaxException | IOException ex) {
			log.log(Level.SEVERE, null, ex);
			res[0] = 2;
		}
	}
	
	void execute(ArrayList<Item> items, String dir1, String dir2, int[] cpt) {
		var i = items.iterator();
		while (i.hasNext()) {
			var item = i.next();
			if (item.select) {
				var relPath2 = item.path2.substring(dir2.length() + 1);
				var relPath1 = dir1 + "/" + relPath2;
				log.log(Level.INFO, "{0} {1} to {2}", new Object[]{item.action, item.path1, relPath1});
				try {
					FileUtils.moveFile(FileUtils.getFile(item.path1), FileUtils.getFile(relPath1));
					cpt[0]++;
				}
				catch (IOException ex) {
					log.log(Level.SEVERE, null, ex);
					cpt[1]++;
				}
				i.remove();
			}
		}
	}

}
