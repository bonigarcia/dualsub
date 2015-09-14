[![][Logo]][Site]

Copyright &copy; 2014 Boni Garcia. Licensed under [GPL v3].

DualSub
=======

DualSub is a tool which allows you to **merge two SRT subtitles** in a single file. The idea of using merged subtitles (let's call them *dual subtitles*) is to watch movies/series in original version with two sets of subtitles. *Why is this useful?* Dual subtitles can be used to learn a second language while watching movies/series. The typical case is when your mother tongue is not English but you want to watch movies/series in English. Then you have several options: 1. Learn English; 2. Watch it with subtitles; 3. Watch it translated to your mother language; 4. Do not watch it. DualSub is a tool for those people between options 1 and 2.

If your English is not perfect, you usually face to idioms, words and expression you do not know. By watching movies/series with dual subtitles (i.e. its original subtitles plus a translation) you will never be in doubt anymore of those expressions you do not understand, since the translation is instantaneous.

If you want to create your own dual subtitles, you need the video to be played with its subtitles. Ideally, you should have two subtitles SRT files for a single video (for instance, the first SRT subtitle in English and the second one in other language). There are plenty of web sites available to collect SRT files, for example <a href="http://www.opensubtitles.org/">opensubtitles</a>, <a href="http://subscene.com/">subscene</a>, <a href="http://www.subtitulos.es/">subtitulos.es</a>, and so on. If you only have a SRT file, you can also <strong>translate</strong> it automatically using DualSub.

Once you have your video and SRT subtitle(s), the next important step is to decide which player you are going to use to play it. This decision is important since it will determine the available width in which subtitles are going to be displayed. Take into account that the double of text subtitles is going to be displayed. For that reason, you need to specify the player/subtitles features. These parameters are: screen width (in pixels); subtitles font family (Arial, Tahoma, ...); subtitles font size (in points). Nevertheless, the way of working of the most of the players regarding the display of subtitles is quite tricky. For example, VLC shows the size of the subtitles proportional to the video size. For this reason, this setup usually has to be tuned using trial and error.

You can see an example of dual subtitles in [YouTube]. It is a piece of Kill Bill Volume 1 with English an Spanish subtitles. Be careful if you have not seen this movie since it is the end of the movie and contains important spoilers!!!

Source
------

DualSub is open source, released under the terms of [GPL v3]. The source code of this project can be cloned from the [GitHub Repository].

Compile
-------

To compile the app run `mvn clean compile assembly:single`

Downloads
---------

DualSub has been developed in Java. Binary releases for Windows, Linux, and Mac OS X are available on [GitHub Releases].

About
-----

DualSub is a personal project of [Boni Garcia]. Comments, questions and suggestions are always very welcome!

[Logo]: http://bonigarcia.github.io/dualsub/img/dualsub.png
[GPL v3]: https://www.gnu.org/copyleft/gpl.html
[Site]: http://bonigarcia.github.io/dualsub/
[GitHub Repository]: https://github.com/bonigarcia/dualsub
[Boni Garcia]: http://bonigarcia.github.io/
[GitHub Releases]: https://github.com/bonigarcia/dualsub/releases
[YouTube]: https://www.youtube.com/watch?v=GwaeRt9bOL0
