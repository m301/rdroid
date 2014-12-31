<?PHP
session_start();
ini_set('include_path', '.:/home/mads8929/public_html/');
include("rdroid/functions.php");
$id = $_SESSION['sID'];
$usr = cde($_SESSION['id'], $id);
$name = trim(urldecode($_GET['device']));
if (checkValid($id, $usr, $name)) {
    include("include/templateClass.php");
    $page = new Template($_SERVER);
    $page->settings = array('fb_like' => 0,
        'rdroid' => 1,
        'no_side_menu' => 1,
        'jq-1.9' => 1,
        'jq-1.10-ui' => 1,
        'jq.tagit' => 0,
        'ui-lightness' => 1,
    );
    $page->title = "Misc" . $SOFTNAME;
    $page->article_title = "Misc" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <script type="text/javascript">
        $(function () {

            $("#action").click(function () {
                $("#wait").show(900);
                $("#opt").hide(700);
                var request = $.ajax({
                    type: "POST",
                    url: "/do.php?device=<?=$name?>&main=misc&sub=" + $("#title").text(),
                    data: {
                        inp1: $('#inp1').val(),
                        inp2: $('#inp2').val(),
                        txt1: $("#clptxt").val()
                    },
                    timeout: 60000
                });
                request.done(function (msg) {
                    $("#response").show(700);
                    $("#wait").hide(700);
                    $("#sresponse").html(msg);
                });
                request.fail(function (jqXHR, textStatus) {
                    console.log("Request failed. Reason : " + textStatus, "");
                    $("#wait").hide(700);
                    $("#response").hide(700);
                    $("#opt").show(700);
                });
            });
            $("#clprefresh,#clprefresh2").click(function (e) {
                $("#wait").show(900);
                $("#opt").hide(700);
                if ($(this).text() == "Sync") {
                    var rurl = "/webapi.php?device=<?=$name?>&action=getclpbrd&sync=1";
                } else {
                    var rurl = "/webapi.php?device=<?=$name?>&action=getclpbrd";
                }
                var request = $.ajax({type: "POST", url: rurl, data: "", timeout: 60000});
                request.done(function (msg) {
                    $("#wait").hide(700);
                    $("#opt").show(700);
                    $("#opt > #clpbrd").show(1);
                    $("#clptxt").val(msg);
                });
                request.fail(function (jqXHR, textStatus) {
                    console.log("Request failed. Reason : " + textStatus, "");
                    $("#wait").hide(700);
                    $("#response").hide(700);
                    $("#opt").show(700);
                });
            });

            $("#home").click(function () {
                $("#opt").hide(700);
                $("#main").show(700);
            });
            $("#back").click(function () {
                $("#response").hide(700);
                $("#opt").show(700);
            });

            $("#beeper").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #clpbrd").hide(1);
                $("#opt > #title").text("beep");
                $("#opt > #action").html("Beep My Phone");
                $("#lbl1").html("Time");
                $("#lbl2").html("Frequency ");
                $("#op1").show(1);
                $("#op2").show(1);
            });
            $("#clipboard").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #title").text("setclipboard");
                $("#opt > #action").html("Save");
                $("#op1").hide(1);
                $("#op2").hide(1);
                $("#opt > #clpbrd").show(1);
            });
            $("#ourl").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #clpbrd").hide(1);
                $("#lbl1").html("URL :");
                $("#opt > #title").text("openurl");
                $("#opt > #action").html("Open It");
                $("#op1").show(1);
                $("#op2").hide(1);
            });
            $("#setfrmurl").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #clpbrd").hide(1);
                $("#lbl1").html("URL :");
                $("#opt > #title").text("setaswallpaperfromurl");
                $("#opt > #action").html("Get my new Wallpaper");
                $("#op1").show(1);
                $("#op2").hide(1);
            });
            $("#downloader").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #clpbrd").hide(1);
                $("#lbl1").html("URL ");
                $("#lbl2").html("File Name ");
                $("#opt > #title").text("download");
                $("#opt > #action").html("Start downloading");
                $("#op1").show(1);
                $("#op2").show(1);
            });
            $("#vibrator").click(function () {
                $("#opt").show(700);
                $("#main").hide(700);
                $("#opt > #clpbrd").hide(1);
                $("#lbl1").html("Time (Seconds) :");
                $("#opt > #title").text("vibrate");
                $("#opt > #action").html("Ya..vibrate it");
                $("#op1").show(1);
                $("#op2").hide(1);
            });
            $("input[type=submit]").click(function (event) {
                event.preventDefault();
            });
            $('input[type=text]').button().css({'font': 'inherit', 'color': 'inherit', 'text-align': 'left', 'outline': 'none', 'cursor': 'text'});
        });
    </script>
    <style>

    </style>

    <?PHP $page->PrintBetweenHeadAndBody(); ?>
    <table style="border-style: dotted; border-width: 5px;" cellpadding="15" align="center" id="maintable">
        <tr>
            <td>


                <div id="wait" style="display: none;"><img src="/images/Loading.gif"/></div>
                <div id="fade"></div>
                <div class="popupbox" id="popuprel">

                    <div id="response" style="display: none;">
                        <div id="sresponse"></div>
                        <br/>
                        <a href='#' style='width:100;' class='metro' id='back'>Back</a>
                    </div>

                </div>

                <!-- Main -->
                <div id="main">
                    <a href='#' class='metro' id='beeper'>Beeper</a><br/>
                    <a href='#' class='metro' id='clipboard'>Clipboard</a><br/>
                    <a href='#' class='metro' id='ourl'>Open URL</a><br/>
                    <a href='#' class='metro' id='setfrmurl'>Set Wallpaper[From URL]</a><br/>
                    <a href='#' class='metro' id='downloader'>Downloader</a><br/>
                    <a href='#' class='metro' id='vibrator'>Vibrator</a><br/>
                </div>
                <!-- Main End -- Options Start-->
                <div id="opt" style="display: none;">
                    <h2 id="title" style="display: none;"></h2>

                    <div id="op1">
                        <label id="lbl1" for="inp1" class="ui-widget ui-state-default"
                               style="position:relative;top:+5px;width:auto"></label><input type="text" id="inp1"
                                                                                            style="float:right;"/>
                    </div>
                    <div id="op2">
                        <label id="lbl2" for="inp2" class="ui-widget ui-state-default"
                               style="position:relative;top:+30px"></label><input type="text" id="inp2"
                                                                                  style="float:right;"/>
                    </div>
                    <br/>

                    <div id="clpbrd">
                        <a href='#' class='metro' id='clprefresh' style="float:right;">Sync</a>
                        <a href='#' class='metro' id='clprefresh2' style="float:right;">Refresh</a><br/>
                        <textarea id="clptxt" class="ui-widget ui-state-default ui-corner-all" rows="8"
                                  cols="40"></textarea>
                    </div>


                    <a href='#' class='metro' id='action'></a><a href='#' style='width:100;' class='metro' id='home'>Back</a>
                </div>
                <!-- Options End -->


            </td>
        </tr>
    </table>

    <?PHP $page->PrintAfterBody();
} ?>