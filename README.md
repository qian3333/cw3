***How to use:***
start the program. Click run button to show/unshow the badge. You can change the profile picture in /res/drawable , replace the profile.png

***Explaination:*** 
This Kotlin Jetpack Compose code defines a simple Android UI that displays a circular profile image with an optional notification badge and a button to toggle the badge. The app uses MainActivity to set up an edge-to-edge themed Scaffold and places the ProfileScreen composable inside it. ProfileScreen manages a local boolean state sBadge (remembered via mutableStateOf) which controls whether the badge is shown; it lays out the avatar using ProfileAvatar(showBadge = sBadge) and provides a Button that flips sBadge and updates its label between "hide" and "show". ProfileAvatar composes a circular clipped Image with a white border and drop shadow, and when showBadge is true it overlays a small circular red gradient badge at the avatar’s bottom-right containing a white notification icon. The code uses Material3 theming, spacing with Modifier.padding and Modifier.size, and visual effects like shadow, border, and Brush.linearGradient to achieve the desired appearance.

***AI REFLECTOIN***
I asked the AI about how to set up the avatar image and applied that to my code. I also asked the AI for code for the badge styling. The shortcoming is that my badge is partially obscured, and the AI didn't provide a very good solution for that. At the same time, I think the AI's part about the badge gradient colors was very good — it really helps emphasize the icon.
