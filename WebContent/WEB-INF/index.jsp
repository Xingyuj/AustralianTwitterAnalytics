<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/templatemo-style.css">
    <link rel="stylesheet" type="text/css" href="css/googlemap-style.css">
    <!--
    Puzzle Template
    http://www.templatemo.com/tm-477-puzzle
-->
    <title>CloudComputing</title>
</head>

<body>
<div class="ipAddress">
  <span id="ip" style="display:none"><c:out value="${ipAddress.ip}" /></span>
  <span id="port" style="display:none"><c:out value="${ipAddress.port}" /></span>
</div>
<div class="fixed-header">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">CouldComputing</a>
        </div>
        <nav class="main-menu">
            <ul>
                <li><a href="#home">S1</a></li>
                <li><a href="#S2">S2</a></li>
                <li><a href="#S3">S3</a></li>
                <li><a href="#S4">S4</a></li>
                <li><a href="#about">About</a></li>
            </ul>
        </nav>
    </div>
</div>

<div class="container">
    <section class="col-md-12 content" id="home">
        <div class="col-lg-9 col-md-9 content-item">
            <div id="googft-mapCanvas" alt="map"></div>
            <input id="googft-legend-open" style="display:none" type="button" value="Legend">

            <div id="googft-legend">
                <p id="googft-legend-title">Wealth</p>

                <div>
                    <span class="googft-legend-swatch" style="background-color: #ffe599"></span>
                    <span class="googft-legend-range">205</span>
                </div>
                <div>
                    <span class="googft-legend-swatch" style="background-color: #ffd966"></span>
                    <br/>
                </div>
                <div>
                    <span class="googft-legend-swatch" style="background-color: #bf9000"></span>
                    <br/>
                </div>
                <div>
                    <span class="googft-legend-swatch" style="background-color: #7f6000"></span>
                    <span class="googft-legend-range">1000</span>
                </div>

                <hr>

                <div>
						<span class="googft-legend-swatch"
                              style="background-color: #ffffff"></span>
                    <span class="googft-legend-range">Null</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide">
            </div>

            <input id="googft-legend-open" style="display:none" type="button" value="Legend">

            <div id="googft-legend">
                <p id="googft-legend-title">Happy %</p>

                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NijL7v3p1+v8zZ6rAdGCg4X+g+EyYorS0NNv////PxMCxsRYghbEgRQcOHCjGqmjv3kKQor0gRQ8fPmzHquj27WaQottEmxQLshubopAQI5CiEJjj54N8t3FjFth369ZlwHw3jQENgMJpIzSc1iGHEwB8p5qDBbsHtAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0 to 0.1</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiElEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81izL7n746/X/VmSowbRho+B8oPhOmKM02zfb/TCzQItYCpDAWpOhA8YFirIoK9xaCFO0FKXrY/rAdq6Lm280gRbeJNikWZDc2RUYhRiBFITDHzwf5LmtjFth3GesyYL6bxoAGQOG0ERpO65DDCQDX7ovT++K9KQAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.1 to 0.2</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81M4v6n56++n9V1RkwbWgY+B8oPhOmKM3WNu3/zJn/MbCFRSxIYSxI0YHi4gNYFRUW7gUp2gtS9LC9/SFWRc3Nt0GKbhNtUizIbmyKjIxCQIpCYI6fD/JdVtZGsO8yMtbBfDeNAQ2AwmkjNJzWIYcTAMk+i9OhipcQAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.2 to 0.301</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide">
            </div>
        </div>
        <div class="col-lg-3 col-md-3 content-item content-item-1 background">
            <h2 class="text-center dark-blue-text">Scenario 1</h2>

            <p class="dark-blue-text">+<strong> Wealth(Aurin)</strong></p>

            <p class="dark-blue-text">+<strong> Emotion(Twitter)</strong></p>
            <hr>
            <p>The first scenario indicates that the relationship of emotion and wealth of human life.</p>
            <hr>
            <div class="checkbox">
                <ul>
                    <li><input type="checkbox" name="aurin" id="checkbox-aurin" checked>AURIN Data<br></li>
                    <li><input type="checkbox" name="twitter" id="checkbox-twitter">Twitter Data<br></li>
                </ul>
            </div>
            <div>
                <!-- <hr>
                <button type="button" class="btn dark-blue-bordered-btn normal-btn">View Map</button> -->
            </div>
        </div>
    </section>

    <section class="col-md-12 content padding">
        <hr>
    </section>

    <section class="col-md-12 content padding" id="S2">
        <div class="col-lg-3 col-md-3 content-item content-item-1 background">
            <h2 class="text-center dark-blue-text">Scenario 2</h2>

            <p class="dark-blue-text">+<strong> Attitudes in Daytime and Night</strong></p>
            <hr>
            <p>
                This scenario calculates the proportion of positive tweet in the morning and evening.
            </p>
            <hr>
            <div class="checkbox">
                <ul>
                    <li><input type="checkbox" name="aurin" id="checkbox-aurin" checked>Morning Data<br></li>
                    <li><input type="checkbox" name="twitter" id="checkbox-twitter">Night Data<br></li>
                </ul>
            </div>
        </div>

        <div class="col-lg-9 col-md-9 content-item">
            <!-- <img src="images/1.jpg" alt="Image" class="tm-image"> -->
            <div id="googft-mapCanvas" alt="map"></div>
            <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

            <div id="googft-legend">
                <p id="googft-legend-title">Daytime Positive</p>

                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NijL7v3p1+v8zZ6rAdGCg4X+g+EyYorS0NNv////PxMCxsRYghbEgRQcOHCjGqmjv3kKQor0gRQ8fPmzHquj27WaQottEmxQLshubopAQI5CiEJjj54N8t3FjFth369ZlwHw3jQENgMJpIzSc1iGHEwB8p5qDBbsHtAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0 to 0.05</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiElEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81izL7n746/X/VmSowbRho+B8oPhOmKM02zfb/TCzQItYCpDAWpOhA8YFirIoK9xaCFO0FKXrY/rAdq6Lm280gRbeJNikWZDc2RUYhRiBFITDHzwf5LmtjFth3GesyYL6bxoAGQOG0ERpO65DDCQDX7ovT++K9KQAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.05 to 0.09</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81M4v6n56++n9V1RkwbWgY+B8oPhOmKM3WNu3/zJn/MbCFRSxIYSxI0YHi4gNYFRUW7gUp2gtS9LC9/SFWRc3Nt0GKbhNtUizIbmyKjIxCQIpCYI6fD/JdVtZGsO8yMtbBfDeNAQ2AwmkjNJzWIYcTAMk+i9OhipcQAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.09 to 0.13</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NMov6vzp99f8zVWfAdKBh4H+g+EyYorQ027T//2f+x8CxFrEghbEgRQcOFB/Aqmhv4V6Qor0gRQ8ftj/Equh2822QottEmxQLshubohCjEJCiEJjj54N8tzFrI9h36zLWwXw3jQENgMJpIzSc1iGHEwBt95qDejjnKAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.13 to 0.2</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NUlH5v9rF5f+ZoCAwHaig8B8oPhOmKC1NU/P//7Q0DByrqgpSGAtSdOCAry9WRXt9fECK9oIUPXwYFYVV0e2ICJCi20SbFAuyG5uiECUlkKIQmOPng3y30d0d7Lt1bm4w301jQAOgcNoIDad1yOEEAFm9fSv/VqtJAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.2 to 0.3</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
            </div>

            <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

            <div id="googft-legend">
                <p id="googft-legend-title">Night Positive</p>

                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NijL7v3p1+v8zZ6rAdGCg4X+g+EyYorS0NNv////PxMCxsRYghbEgRQcOHCjGqmjv3kKQor0gRQ8fPmzHquj27WaQottEmxQLshubopAQI5CiEJjj54N8t3FjFth369ZlwHw3jQENgMJpIzSc1iGHEwB8p5qDBbsHtAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0 to 0.015</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiElEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81izL7n746/X/VmSowbRho+B8oPhOmKM02zfb/TCzQItYCpDAWpOhA8YFirIoK9xaCFO0FKXrY/rAdq6Lm280gRbeJNikWZDc2RUYhRiBFITDHzwf5LmtjFth3GesyYL6bxoAGQOG0ERpO65DDCQDX7ovT++K9KQAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.015 to 0.05</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81M4v6n56++n9V1RkwbWgY+B8oPhOmKM3WNu3/zJn/MbCFRSxIYSxI0YHi4gNYFRUW7gUp2gtS9LC9/SFWRc3Nt0GKbhNtUizIbmyKjIxCQIpCYI6fD/JdVtZGsO8yMtbBfDeNAQ2AwmkjNJzWIYcTAMk+i9OhipcQAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.05 to 0.08</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NMov6vzp99f8zVWfAdKBh4H+g+EyYorQ027T//2f+x8CxFrEghbEgRQcOFB/Aqmhv4V6Qor0gRQ8ftj/Equh2822QottEmxQLshubohCjEJCiEJjj54N8tzFrI9h36zLWwXw3jQENgMJpIzSc1iGHEwBt95qDejjnKAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.08 to 0.15</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NUlH5v9rF5f+ZoCAwHaig8B8oPhOmKC1NU/P//7Q0DByrqgpSGAtSdOCAry9WRXt9fECK9oIUPXwYFYVV0e2ICJCi20SbFAuyG5uiECUlkKIQmOPng3y30d0d7Lt1bm4w301jQAOgcNoIDad1yOEEAFm9fSv/VqtJAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.15 to 0.25</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
            </div>

        </div>
    </section>

    <section class="col-md-12 content padding">
        <hr>
    </section>

    <section class="col-md-12 content" id="S3">
        <div class="col-lg-9 col-md-9 content-item">
            <div id="googft-mapCanvas" alt="map"></div>
            <div id="aurin-legend">
                <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

                <div id="googft-legend">
                    <p id="googft-legend-title">Education Rate</p>

                    <div>
                        <span class="googft-legend-swatch" style="background-color: #e0ffd4"></span>
                        <span class="googft-legend-range">0.645</span>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #c2f79b"></span>
                        <br/>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #50aa00"></span>
                        <br/>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #267114"></span>
                        <span class="googft-legend-range">1.187</span>
                    </div>
                    <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
                </div>
            </div>

            <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

            <div id="googft-legend">
                <p id="googft-legend-title">Profanity Rate</p>

                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NijL7v3p1+v8zZ6rAdGCg4X+g+EyYorS0NNv////PxMCxsRYghbEgRQcOHCjGqmjv3kKQor0gRQ8fPmzHquj27WaQottEmxQLshubopAQI5CiEJjj54N8t3FjFth369ZlwHw3jQENgMJpIzSc1iGHEwB8p5qDBbsHtAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0 to 0.01</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiElEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81izL7n746/X/VmSowbRho+B8oPhOmKM02zfb/TCzQItYCpDAWpOhA8YFirIoK9xaCFO0FKXrY/rAdq6Lm280gRbeJNikWZDc2RUYhRiBFITDHzwf5LmtjFth3GesyYL6bxoAGQOG0ERpO65DDCQDX7ovT++K9KQAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.01 to 0.02</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81M4v6n56++n9V1RkwbWgY+B8oPhOmKM3WNu3/zJn/MbCFRSxIYSxI0YHi4gNYFRUW7gUp2gtS9LC9/SFWRc3Nt0GKbhNtUizIbmyKjIxCQIpCYI6fD/JdVtZGsO8yMtbBfDeNAQ2AwmkjNJzWIYcTAMk+i9OhipcQAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.02 to 0.03</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NMov6vzp99f8zVWfAdKBh4H+g+EyYorQ027T//2f+x8CxFrEghbEgRQcOFB/Aqmhv4V6Qor0gRQ8ftj/Equh2822QottEmxQLshubohCjEJCiEJjj54N8tzFrI9h36zLWwXw3jQENgMJpIzSc1iGHEwBt95qDejjnKAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.03 to 0.06</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NUlH5v9rF5f+ZoCAwHaig8B8oPhOmKC1NU/P//7Q0DByrqgpSGAtSdOCAry9WRXt9fECK9oIUPXwYFYVV0e2ICJCi20SbFAuyG5uiECUlkKIQmOPng3y30d0d7Lt1bm4w301jQAOgcNoIDad1yOEEAFm9fSv/VqtJAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.06 to 0.101</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
            </div>
        </div>
        <div class="col-lg-3 col-md-3 content-item content-item-1 background">
            <h2 class="text-center dark-blue-text">Scenario 3</h2>

            <p class="dark-blue-text">+<strong> Swearing Rate</strong></p>

            <p class="dark-blue-text">+<strong> Education Rate</strong></p>
            <hr>
            <p>
                The third scenario represents the relationship between the degree of swearing(Twitter) and the degree of
                education(AURIN).
            </p>
            <hr>
            <div class="checkbox">
                <input type="checkbox" name="aurin" id="checkbox-aurin" checked>AURIN Data<br>
                <input type="checkbox" name="twitter" id="checkbox-twitter">Twitter Data<br>
            </div>
        </div>
    </section>

    <section class="col-md-12 content padding">
        <hr>
    </section>

    <section class="col-md-12 content padding" id="S4">
        <div class="col-lg-3 col-md-3 content-item content-item-1 background">
            <h2 class="text-center dark-blue-text">Scenario 4</h2>

            <p class="dark-blue-text">+<strong> Profanity Rate</strong></p>

            <p class="dark-blue-text">+<strong> Crime Rate</strong></p>
            <hr>

            <p>
                The fourth scenario discusses the issue that whether the degree of using profanity(Twitter) relates to
                the proportion of crime(AURIN).
            </p>
            <hr>
            <div class="checkbox">
                <ul>
                    <li><input type="checkbox" name="aurin" id="checkbox-aurin" checked>AURIN Data<br></li>
                    <li><input type="checkbox" name="twitter" id="checkbox-twitter">Twitter Data<br></li>
                </ul>
            </div>
        </div>

        <div class="col-lg-9 col-md-9 content-item">
            <!-- <img src="images/1.jpg" alt="Image" class="tm-image"> -->
            <div id="googft-mapCanvas" alt="map"></div>
            <div id="aurin-legend">
                <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

                <div id="googft-legend">
                    <p id="googft-legend-title">Crime Count</p>

                    <div>
                        <span class="googft-legend-swatch" style="background-color: #f4cccc"></span>
                        <span class="googft-legend-range">4</span>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #e06666"></span>
                        <br/>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #990000"></span>
                        <br/>
                    </div>
                    <div>
                        <span class="googft-legend-swatch" style="background-color: #660000"></span>
                        <span class="googft-legend-range">170.7</span>
                    </div>
                    <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
                </div>
            </div>

            <input id="googft-legend-open" style="display:none" type="button" value="Legend"></input>

            <div id="googft-legend">
                <p id="googft-legend-title">Profanity %</p>

                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NijL7v3p1+v8zZ6rAdGCg4X+g+EyYorS0NNv////PxMCxsRYghbEgRQcOHCjGqmjv3kKQor0gRQ8fPmzHquj27WaQottEmxQLshubopAQI5CiEJjj54N8t3FjFth369ZlwHw3jQENgMJpIzSc1iGHEwB8p5qDBbsHtAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0 to 0.01</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiElEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81izL7n746/X/VmSowbRho+B8oPhOmKM02zfb/TCzQItYCpDAWpOhA8YFirIoK9xaCFO0FKXrY/rAdq6Lm280gRbeJNikWZDc2RUYhRiBFITDHzwf5LmtjFth3GesyYL6bxoAGQOG0ERpO65DDCQDX7ovT++K9KQAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.01 to 0.02</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC81M4v6n56++n9V1RkwbWgY+B8oPhOmKM3WNu3/zJn/MbCFRSxIYSxI0YHi4gNYFRUW7gUp2gtS9LC9/SFWRc3Nt0GKbhNtUizIbmyKjIxCQIpCYI6fD/JdVtZGsO8yMtbBfDeNAQ2AwmkjNJzWIYcTAMk+i9OhipcQAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.02 to 0.03</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAi0lEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NMov6vzp99f8zVWfAdKBh4H+g+EyYorQ027T//2f+x8CxFrEghbEgRQcOFB/Aqmhv4V6Qor0gRQ8ftj/Equh2822QottEmxQLshubohCjEJCiEJjj54N8tzFrI9h36zLWwXw3jQENgMJpIzSc1iGHEwBt95qDejjnKAAAAABJRU5ErkJggg=="/>
                    <span class="googft-legend-range">0.03 to 0.06</span>
                </div>
                <div>
                    <img class="googft-dot-icon"
                         src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAJCAYAAADgkQYQAAAAiklEQVR42mNgQIAoIF4NxGegdCCSHAMzEC+NUlH5v9rF5f+ZoCAwHaig8B8oPhOmKC1NU/P//7Q0DByrqgpSGAtSdOCAry9WRXt9fECK9oIUPXwYFYVV0e2ICJCi20SbFAuyG5uiECUlkKIQmOPng3y30d0d7Lt1bm4w301jQAOgcNoIDad1yOEEAFm9fSv/VqtJAAAAAElFTkSuQmCC"/>
                    <span class="googft-legend-range">0.06 to 0.101</span>
                </div>
                <input id="googft-legend-close" style="display:none" type="button" value="Hide"></input>
            </div>
        </div>
    </section>

    <section class="col-md-12 content padding">
        <hr>
    </section>

    <footer class="col-md-12 content" id="about">
        <div class="col-lg-6 col-md-6 last">
            <img src="images/5.png" alt="Image" class="tm-image">
        </div>
        <div class="col-lg-6 col-md-6 background last about-text-container">
            <h2 class="section-title">About Us</h2>
            <table>
                <tr>
                    <td>Lindong Li</td>
                    <td> | <a href="#about">lindongl@student.unimelb.edu.au</a></td>
                </tr>
                <tr>
                    <td>Wen Guo</td>
                    <td> | <a href="#about">guow1@student.unimelb.edu.au</a></td>
                </tr>
                <tr>
                    <td>Xinyu Ji</td>
                    <td> | <a href="#about">xinyuj@student.unimelb.edu.au</a></td>
                </tr>
                <tr>
                    <td>Chennan Gu</td>
                    <td> | <a href="#about">chennang@student.unimelb.edu.au</a></td>
                </tr>
                <tr>
                    <td>Jie he</td>
                    <td> | <a href="#about">jieh3@student.unimelb.edu.au</a></td>
                </tr>
            </table>
        </div>
    </footer>

</div>

<div class="text-center footer">
    <div class="container">
        Copyright @ 2016 CloudComputing

        | Design: ChennanGu
    </div>
</div>

<script src="JS/lib/jquery.min.js"></script>
<script src="JS/lib/bootstrap.min.js"></script>
<script src="JS/lib/jquery.singlePageNav.min.js"></script>
<script type="text/javascript" src="https://maps.google.com/maps/api/js?v=3"></script>
<!--<script async defer-->
<!--src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCZh8qTZLUNu0KVv7yurzGl3xaV8SNMpL8&callback=initMap">-->
<!--</script>-->
<script src="JS/lib/maps.js"></script>
<script>

    // Check scroll position and add/remove background to navbar
    function checkScrollPosition() {
        if ($(window).scrollTop() > 50) {
            $(".fixed-header").addClass("scroll");
        } else {
            $(".fixed-header").removeClass("scroll");
        }
    }

    $(document).ready(function () {
        // Single page nav
        $('.fixed-header').singlePageNav({
            offset: 59,
            filter: ':not(.external)',
            updateHash: true
        });

        checkScrollPosition();

        // nav bar
        $('.navbar-toggle').click(function () {
            $('.main-menu').toggleClass('show');
        });

        $('.main-menu a').click(function () {
            $('.main-menu').removeClass('show');
        });
    });

    $(window).on("scroll", function () {
        checkScrollPosition();
    });
</script>
</body>
</html>
