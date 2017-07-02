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
    $page->title = "Settings" . $SOFTNAME;
    $page->article_title = "Settings" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <style>
        legend {
            border: 1px black solid;
            margin-left: 1em;
            padding: 0.2em 0.8em;
        }
    </style>
    <script type="text/javascript">
        $(function () {

            $("#main").on("click", "#save1", function () {
                var ths = $(this);
                post(ths.parent("#fdata").data("fid"), ths.parent("#fdata").data("fname") + "=" + ths.data("fvalue"));
                return false;
            });

            $("#home").click(function () {
                $("#response").hide(700);
                $("#main").show(700);
            });


        });
        function hideAll() {

        }
        function post(method, data) {
            hideAll();
            $("#wait").show(900);
            $("#main").hide(700);
            var request = $.ajax({
                type: "POST",
                url: "/do.php?device=<?=$name?>&main=settings&sub=" + method,
                data: data,
                timeout: 60000
            });
            request.done(function (msg) {
                $("#response").show(700);
                $("#wait").hide(700);
                $("#sresponse").html(msg);
            });
            request.fail(function (jqXHR, textStatus) {
                //alert( "Request failed. Reason : "+textStatus);

                $("#wait").hide(700);
                $("#response").hide(700);
                $("#main").show(700);
            });
        }
    </script>


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
                        <a href='#' style='width:100;' class='metro' id='home'>Back</a>
                    </div>

                </div>

                <!-- Main -->
                <div id="main">


                    <fieldset style="border: 1px black solid">
                        <legend>Screen Brightness Mode</legend>
                        <div id="fdata" data-fid="setbrightnessmode" data-fname='mode'>
                            <a href='#' class='metro' id='save1' data-fvalue='0'>Manual</a>
                            <a href='#' class='metro' id='save1' data-fvalue='1'>Automatic</a>
                        </div>
                    </fieldset>
                    <br/>


                    <fieldset style="border: 1px black solid">
                        <legend>Mute</legend>
                        <div id="fdata" data-fid="mute" data-fname='channel'>
                            <a href='#' class='metro' id='save1' data-fvalue='0'>Voice Call</a>
                            <a href='#' class='metro' id='save1' data-fvalue='1'>System</a>
                            <a href='#' class='metro' id='save1' data-fvalue='2'>Ring</a>
                            <a href='#' class='metro' id='save1' data-fvalue='3'>Notification</a>
                            <a href='#' class='metro' id='save1' data-fvalue='4'>Music</a>
                            <a href='#' class='metro' id='save1' data-fvalue='5'>Alarm</a>
                        </div>
                    </fieldset>
                    <br/>


                    <fieldset style="border: 1px black solid">
                        <legend>Ringer Mode</legend>
                        <div id="fdata" data-fid='ringermode' data-fname="mode">
                            <a href='#' class='metro' id='save1' data-fvalue='0'>Silent</a>
                            <a href='#' class='metro' id='save1' data-fvalue='1'>Vibrate</a>
                            <a href='#' class='metro' id='save1' data-fvalue='2'>Normal</a>
                        </div>
                    </fieldset>
                    <br/>


                    <fieldset style="border: 1px black solid">
                        <legend>Switch</legend>
                        <!--display:inline;-->
                        <fieldset style="border: 1px black solid;">
                            <legend>Turn On</legend>
                            <div id="fdata" data-fid='switch' data-fname="action=1&type">
                                <a href='#' class='metro' id='save1' data-fvalue='1'>Wifi</a>
                                <a href='#' class='metro' id='save1' data-fvalue='4'>GPS</a>
                                <a href='#' class='metro' id='save1' data-fvalue='7'>Bluetooth</a>
                                <a href='#' class='metro' id='save1' data-fvalue='5'>Packet Data</a>
                                <a href='#' class='metro' id='save1' data-fvalue='8'>Airplane Mode</a>
                            </div>
                        </fieldset>
                        <br/>

                        <fieldset style="border: 1px black solid;">
                            <legend>Turn Off</legend>
                            <div id="fdata" data-fid='switch' data-fname="action=0&type">
                                <a href='#' class='metro' id='save1' data-fvalue='1'>Wifi</a>
                                <a href='#' class='metro' id='save1' data-fvalue='4'>GPS</a>
                                <a href='#' class='metro' id='save1' data-fvalue='7'>Bluetooth</a>
                                <a href='#' class='metro' id='save1' data-fvalue='5'>Packet Data</a>
                                <a href='#' class='metro' id='save1' data-fvalue='8'>Airplane Mode</a>
                            </div>
                        </fieldset>
                        <br/>

                        <fieldset style="border: 1px black solid;">
                            <legend>Toogle</legend>
                            <div id="fdata" data-fid='switch' data-fname="action=2&type">
                                <a href='#' class='metro' id='save1' data-fvalue='1'>Wifi</a>
                                <a href='#' class='metro' id='save1' data-fvalue='4'>GPS</a>
                                <a href='#' class='metro' id='save1' data-fvalue='7'>Bluetooth</a>
                                <a href='#' class='metro' id='save1' data-fvalue='5'>Packet Data</a>
                                <a href='#' class='metro' id='save1' data-fvalue='8'>Airplane Mode</a>
                            </div>
                        </fieldset>
                    </fieldset>
                    <br/>

                </div>


            </td>
        </tr>
    </table>
    <div id="mactive" style="display:none;"></div>
    <?PHP $page->PrintAfterBody();
} ?>