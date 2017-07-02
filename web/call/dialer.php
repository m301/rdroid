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
        'jq.multiselect' => 1,
        'jq.multiselect.filter' => 1,
        'ui-lightness' => 1,
    );
    $page->title = "Dialer" . $SOFTNAME;
    $page->article_title = "Dialer" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>

    <script type="text/javascript">
        $(function () {

            $("#skillcall").click(function () {
                $("#response").hide(700);
                $("#wait").show(700);
                var request = $.ajax({
                    type: "GET",
                    url: "/do.php?device=<?=$name?>&main=call&sub=killcall",
                    data: {},
                    timeout: 60000
                });
                request.done(function (msg) {
                    $("#response").show(700);
                    $("#wait").hide(700);
                    $("#sresponse").html(msg);
                    $("#skillcall").hide(700);
                });
                request.fail(function (jqXHR, textStatus) {
                    alert("Request failed. Reason : " + textStatus);
                    $("#wait").hide(700);
                    $("#response").hide(700);
                    $("#dialer").show(700);
                });
            });

            $("#dial").click(function () {
                $("#dialer").hide(700);
                $("#wait").show(700);
                var request = $.ajax({
                    type: "POST",
                    url: "/do.php?device=<?=$name?>&main=call&sub=dial",
                    data: { number: phone.value,
                        loudspeaker: ls.checked,
                        killafter: killafter.value,
                        volume: volume.value
                    },
                    timeout: 60000
                });
                request.done(function (msg) {
                    $("#response").show(700);
                    $("#wait").hide(700);
                    $("#sresponse").html(msg);
                    $("#skillcall").show(700);
                });
                request.fail(function (jqXHR, textStatus) {
                    alert("Request failed. Reason : " + textStatus);
                    $("#wait").hide(700);
                    $("#response").hide(700);
                    $("#dialer").show(700);
                });
            });
            $("#response").delegate('#showdialer', 'click', function () {
                $("#wait").hide(700);
                $("#response").hide(700);
                $("#dialer").show(700);
            });


            $("#multiselect")
                .multiselect({
                    multiple: false,
                    header: "Contacts",
                    noneSelectedText: "Contacts",
                    selectedList: 1
                })
                .multiselectfilter({
                    autoReset: true,
                    placeholder: 'Name or Number'
                })
                .bind("multiselectclick", function (event, ui) {
                    if (ui.checked) {
                        var e1 = ui.value.split("]");
                        e1 = e1[0].split("[");
                        phone.value = e1[1];
                    }
                    /*
                     event: the original event object

                     ui.value: value of the checkbox
                     ui.text: text of the checkbox
                     ui.checked: whether or not the input was checked
                     or unchecked (boolean)
                     */
                });

            $('#fade').click(function () {
                $('#fade , #popuprel').fadeOut();
                return false;
            });

            $("input[type=submit]")
                // .button()
                .click(function (event) {
                    event.preventDefault();
                });

            $('input[type=text]')
                .button()
                .css({
                    'font': 'inherit',
                    'color': 'inherit',
                    'text-align': 'left',
                    'outline': 'none',
                    'cursor': 'text'
                });

            $("#options").click(function (e) {
                $('#popuprel').fadeIn();
                $('body').append('<div id="fade"></div>');
                $('#fade').css({'filter': 'alpha(opacity=80)'}).fadeIn();
                var popuptopmargin = ($('#popuprel').height() + 10) / 2;
                var popupleftmargin = ($('#popuprel').width() + 10) / 2;
                $('#popuprel').css({
                    'margin-top': -popuptopmargin,
                    'margin-left': -popupleftmargin
                });
                return false;
            });


        });
    </script>
    <style>
        #selectable .ui-selecting {
            background: #FECA40;
        }

        #selectable .ui-selected {
            background: #F39814;
            color: white;
        }

        #selectable {
            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 60%;
        }

        #selectable li {
            margin: 3px;
            padding: 0.4em;
            font-size: 1.4em;
            height: 18px;
        }

        .popupbox {
            width: 350px;
            height: 100px;
            background-color: #ffffff;
            background-repeat: no-repeat;
            display: none; /* Hidden as default */
            float: left;
            position: fixed;
            top: 50%;
            left: 50%;
            z-index: 99999;
            -webkit-box-shadow: 0px 0px 20px #000;
            -moz-box-shadow: 0px 0px 20px #000;
            box-shadow: 0px 0px 20px #000;
            overflow: hidden;
            padding: 5px;
            padding-left: 10px;
            border: solid 4px steelblue;
            -moz-border-radius: 6px 6px 5px 6px;
            -webkit-border-radius: 6px 6px 5px 6px;
            border-radius: 6px 6px 6px 5px;
        }

        #fade {
            display: none; /* Hidden as default */
            background: #000;
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            opacity: .80;
            z-index: 9999;
        }

    </style>

    <?PHP $page->PrintBetweenHeadAndBody(); ?>


    <br/>
    <table style="border-style: dotted; border-width: 5px;" cellpadding="15" align="center">
        <tr>
            <td>
                <div id="wait" style="display: none;"><img src="/images/Loading.gif"/></div>
                <div id="response" style="display: none;">
                    <div id="sresponse"></div>
                    <br/>
                    <a href='#' style='width:100;' class='metro' id='skillcall'>Kill Call</a>
                    <a href='#' style='width:100;' class='metro' id='showdialer'>Show Dialer</a>
                </div>

                <!-- Dialer -->
                <div id="dialer">
                    <a href="#" id="options" align="right">Options</a><br/><br/>
                    <table>
                        <tr>
                            <input name='number' id='phone' style="height: 30px; width: 240px"
                                   value="<?= $_GET['phone'] ?>"/>

                            </td></tr>
                        <tr>
                            <td>
                                <form style="margin:10px 0;margin-left:0px;">
                                    <select multiple="multiple" style="width:240px" id="multiselect">
                                        <?PHP
                                        printContacts($name, 1);
                                        ?>
                                    </select>
                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <img src="android_dialer.png" usemap="#dialermap" border="0" width="240" height="176"
                                     alt=""/>
                                <map name="dialermap">
                                    <area shape="rect" coords="0,0,62,44" alt="1" title="1" onclick="phone.value+='1'"
                                          id="1"/>
                                    <area shape="rect" coords="59,0,121,44" alt="2" title="2" onclick="phone.value+='2'"
                                          id="2"/>
                                    <area shape="rect" coords="121,0,183,44" alt="3" title="3"
                                          onclick="phone.value+='3'" id="3"/>
                                    <area shape="rect" coords="0,43,62,87" alt="4" title="4" onclick="phone.value+='4'"
                                          id="4"/>
                                    <area shape="rect" coords="60,43,122,87" alt="5" title="5"
                                          onclick="phone.value+='5'" id="5"/>
                                    <area shape="rect" coords="120,43,182,87" alt="6" title="6"
                                          onclick="phone.value+='6'" id="6"/>
                                    <area shape="rect" coords="0,86,62,130" alt="7" title="7" onclick="phone.value+='7'"
                                          id="7"/>
                                    <area shape="rect" coords="60,85,122,129" alt="8" title="8"
                                          onclick="phone.value+='8'" id="8"/>
                                    <area shape="rect" coords="121,85,185,129" alt="9" title="9"
                                          onclick="phone.value+='9'" id="9"/>
                                    <area shape="rect" coords="0,127,62,176" alt="*" title="*"
                                          onclick="phone.value+='*'" id="star"/>
                                    <area shape="rect" coords="62,127,124,176" alt="0" title="0"
                                          onclick="phone.value+='0'" id="0"/>
                                    <area shape="rect" coords="123,127,185,176" alt="#" title="#"
                                          onclick="phone.value+='#'" id="hash"/>
                                    <area shape="rect" coords="185,127,240,176" alt="delete" title="Delete"
                                          onclick="phone.value=phone.value.slice(0,-1)" id="delete"/>
                                    <area shape="rect" coords="184,0,240,44" alt="message" title="Message"
                                          id="message"/>
                                    <area shape="rect" coords="183,44,240,128" alt="dial" title="Dial" id="dial"/>
                                </map>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- Dialer END -->
                <?PHP
                $ar = getEVar($name);
                ?>
                <div class="popupbox" id="popuprel">
                    <table>
                        <tr>
                            <td width="40%">
                                <div align="right">Loudspeaker :</div>
                            </td>
                            <td><input type="checkbox" align="left" id="ls" checked="true"
                                       checked="<?= $ar['cLoud'] ?>"/></td>
                        </tr>
                        <tr>
                            <td>
                                <div align="right">Kill After :</div>
                            </td>
                            <td><input type="number" align="left" id="killafter" value="<?= $ar['cKill'] ?>"/> Sec.</td>
                        </tr>
                        <tr>
                            <td>
                                <div align="right">Volume :</div>
                            </td>
                            <td><input type="number" align="left" id="volume" value="<?= $ar['cVol'] ?>"/></td>
                        </tr>

                    </table>
                </div>
                <div id="fade"></div>
        </tr>
    </table>


    <?PHP $page->PrintAfterBody();
} ?>