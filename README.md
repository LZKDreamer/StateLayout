# StateLayout
状态View,包含加载页面，空页面，错误页面，网络错误页面。内含各种状态默认页面，也支持自定义。
[![](https://jitpack.io/v/LZKDreamer/StateLayout.svg)](https://jitpack.io/#LZKDreamer/StateLayout)
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.LZKDreamer:StateLayout:Tag'
	}
