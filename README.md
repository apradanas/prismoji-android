# prismoji-android
####A simple Android Emoji library based on Emoji One 

[![GitHub release](https://img.shields.io/github/release/apradanas/prismoji-android.svg)]()
[![](https://jitpack.io/v/apradanas/prismoji-android.svg)](https://jitpack.io/#apradanas/prismoji-android)

## Installation
###Via Gradle
Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency:

```
dependencies {
	compile 'com.github.apradanas:prismoji-android:1.1.0'
}

```

##Usage
Install the provider. Preverably in Application class.

```
PrismojiManager.install(new PrismojiOneProvider());
```

####Displaying Emoji

```
<com.apradanas.prismoji.PrismojiTextView
	android:id="@+id/prismojiTextView"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	app:emojiSize="26sp" />
```
Call `prismojiTextView.setText` with the String that contains Unicode encoded Emoji

####Inserting Emoji

```
<com.apradanas.prismoji.PrismojiEditText
                android:id="@+id/prismojiEditText"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:emojiSize="26sp" />
```
To open `PrismojiPopup`:

```
PrismojiPopup prismojiPopup = PrismojiPopup.Builder
					.fromRootView(rootView)
                	.setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override
                    public void onKeyboardClose() {
                        prismojiPopup.dismiss();
                    }
                	})
                	.into(prismojiEditText)
                	.build();
                	
prismojiPopup.toggle(); // toggle popup visibility
prismojiPopup.dismiss(); // dismiss the popup
prismojiPopup.isShowing() // return true when the popup is showing
```

##Proguard
No configuration needed.