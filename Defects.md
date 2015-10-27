##Defects Found By Testing

If adding a camera with the same name but a different lens type the app won’t accept it. It throws an error saying that there already exists a camera with that name. It’s possible that the user may have two of the same cameras positioned but with different lenses and we want the app to account for that in the future. This defect was found by simply testing that the GUI elements that are intact worked. This was tested using an android emulator stock with android studio. 

Beyond that, no other defects were found. We used unit tests to check for issues that weren’t GUI related. These unit tests checked to see if the xml files were being generated properly and had accurate information. The unit tests were implemented using JUnit.

Reasons for so few defects?
The software is in its infancy still. Our developers have been working on creating individual parts robustly. So far little integration has happened. So testing has mainly been done on non-integrated components that are unlikely to fail. Also informal testing was done while writing the software to make sure that the part runs. This fragmented approach has been beneficial to us so far. However in the future when these parts become integrated it’s likely that we will encounter many more defects during testing.
