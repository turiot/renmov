The purpose of this small application is to synchronize two initially similar directories, but one of which has renamed and moved files, to apply the same modifications to the second.

The typical use case is that of a backup on 'dir1' of 'dir2'; after classifying files in dir2 (renaming and moving files in sub-directories), if we make a new backup, we are offered copy+deletes on dir1 which is inefficient.

The application detects the actions performed on dir2, relying on the size and the date of update (assumed unchanged by the classification), and proposes to perform the same renaming/moving operations on the target directory dir1.

![sscreenshot](./screenshot.png "")

Operating mode : choose directories 1 (target that will be modified) and 2 (model), press 'search'; select the items to update then press 'execute'.

A setting allows to log the actions in a file, to round the times to the second and to choose the language.
