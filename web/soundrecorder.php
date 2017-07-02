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
        'jq-1.10-ui' => 0,
        'jq.tagit' => 0,
        'ui-lightness' => 0,
    );
    $page->title = "Sound Recorder" . $SOFTNAME;
    $page->article_title = "Sound Recorder" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <script type="text/javascript">
        $(function () {

            $('#start').click(function () {
                sendRequest("/do.php?device=<?=$name?>&main=soundrecorder&sub=record&source=" + $("#src").find(":selected").data("value"));
                return false;
            });
            $('#stop').click(function () {
                sendRequest("/do.php?device=<?=$name?>&main=soundrecorder&sub=stop");
                return false;
            });
            $('#sync').click(function () {
                sendRequest("/do.php?device=<?=$name?>&main=soundrecorder&sub=sync");
                return false;
            });
            $('#refresh').click(function () {
                sendRequest("/webapi.php?device=<?=$name?>&action=sr");
                return false;
            });
            $('#back').click(function () {
                $("#main").show(900);
                $("#response").hide(900);
                return false;
            });

        });
        function sendRequest(sub) {
            $("#wait").show(900);
            $("#main").hide(900);
            var request = $.ajax({
                type: "GET",
                url: sub,
                data: {},
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
                $("#main").show(900);
            });
        }
    </script>
    <style>

    </style>

    <?PHP $page->PrintBetweenHeadAndBody(); ?>
    <table style="border-style: dotted; border-width: 5px;" cellpadding="15" align="center" id="maintable">
        <tr>
            <td>


                <div id="wait" style="display: none;">
                    <img src="/images/Loading.gif"/><br/>
                </div>

                <div id="response" style="display: none;">
                    <div id="sresponse"></div>
                    <br/>
                    <a href='#' style='width:100;' class='metro' id='back'>Back</a>
                </div>

                <div id="main">
                    <a href='#' class='metro' id='start'>Start</a>
                    <a href='#' class='metro' id='stop'>Stop</a>
                    <a href='#' class='metro' id='sync'>Sync Status</a>
                    <a href='#' class='metro' id='refresh'>Get Status</a><br/>

                    <label id="lbl1" for="src" class="ui-widget ui-state-default"
                           style="position:relative;top:+5px;width:auto"></label>
                    <select id="src">
                        <option data-value="0">Default</option>
                        <option data-value="1" selected="selected">Mic</option>
                        <option data-value="2">Call-Outgoing Voice</option>
                        <option data-value="3">Call-Incoming Voice</option>
                        <option data-value="4">Call</option>
                        <option data-value="5">Camcorder</option>
                        <option data-value="6">Speech Recognition</option>
                    </select>
                </div>


            </td>
        </tr>
    </table>

    <?PHP $page->PrintAfterBody();
} ?>