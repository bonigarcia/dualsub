[![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-green.svg)](https://www.gnu.org/licenses/lgpl-3.0)
[![Twitter](https://img.shields.io/badge/follow-@boni_gg-green.svg)](https://twitter.com/boni_gg)

[![][Logo]][Site]

Copyright &copy; 2014-2017 Boni Garcia. Licensed under [GPL v3].

DualSub
=======

DualSub is a tool which allows you to **merge two SRT subtitles** in a single file. The idea of using merged subtitles (let's call them *dual subtitles* or *bilingual subtitles*) is to watch movies/series in original version with two sets of subtitles. This can be used to learn a second language while watching movies/series. Watching movies/series with dual subtitles you will never be in doubt anymore of those expressions you do not understand, since the translation is instantaneous.

If you want to create your own dual subtitles, you need the video to be played with its subtitles. Ideally, you should have two subtitles SRT files for a single video (for instance, the first SRT subtitle in English and the second one in other language). There are plenty of web sites available to collect SRT files, for example [opensubtitles], [subscene], and so on.

DualSub also allows you to **translate SRT subtitles**. Sometimes, it is difficult to find the subtitles for a given content in two different languages. For that reason, DualSub is able to translate subtitles. To that aim, DualSub uses the Google Translate online service. You will be requested to fill up a *captcha* in order to ensure that there is are a human behind the request to Google Translate.

A picture is worth a thousand words:

![][screenshot]


Source
------

DualSub is open source, released under the terms of [GPL v3]. The source code of this project can be cloned from the [GitHub Repository].

DualSub was been created with **Java** and **Maven**. To compile and build the application run the command `mvn clean compile assembly:single`

Downloads
---------

Binary releases for Windows, Linux, and Mac OS X are available on [GitHub Releases].

To convert in command line
--------------------------
Two main classes:
All parameters and properties are taken from DualSub.java
Worker.java is the entrypoint (it is necessary to adapt it), it takes the parameters from DualSub.java

About
-----

DualSub is a personal project of [Boni Garcia]. Comments, questions and suggestions are always very [welcome]!

[Logo]: http://bonigarcia.github.io/dualsub/img/dualsub.png
[GPL v3]: https://www.gnu.org/copyleft/gpl.html
[Site]: http://bonigarcia.github.io/dualsub/
[Boni Garcia]: http://bonigarcia.github.io/
[GitHub Releases]: https://github.com/bonigarcia/dualsub/releases
[opensubtitles]: http://www.opensubtitles.org/
[subscene]: http://subscene.com/
[welcome]: https://groups.google.com/forum/?hl=es#!forum/dualsub
[screenshot]: http://bonigarcia.github.io/dualsub/img/dualsub-collage.png
