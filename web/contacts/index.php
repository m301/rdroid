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
        'no_side_menu' => 1,
        'rdroid' => 1,
        'jq-1.9' => 1,
        'jq-migrate' => 1,
    );
    $page->title = "Contacts" . $SOFTNAME;
    $page->article_title = "Contacts" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>

    <script type="text/javascript">
        function GetURLParameter(sParam) {
            var sPageURL = window.location.search.substring(1);
            var sURLVariables = sPageURL.split('&');
            for (var i = 0; i < sURLVariables.length; i++) {
                var sParameterName = sURLVariables[i].split('=');
                if (sParameterName[0] == sParam) {
                    return sParameterName[1];
                }
            }
        }


        $(document).ready(function () {

            $(document).click(function () {
                $('#action').css({}).animate({opacity: 0, visibility: "hidden"}, 400);
            });
            $(".phone").live("click", function (e) {
                $('#action').css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 400);
                $("#action").width(70);
                $("#action").offset({left: e.pageX + 30, top: e.pageY - 70});
                $("#action").html("<a href='/sms/compose.php?phone=" + encodeURIComponent($(this).html()) + "&device=" + GetURLParameter('device') + "' title='Send SMS'><img src='http://rdroid.madsac.in/images/sms.png' height='30' align='center'/></a><a href='/call/dialer.php?phone=" + encodeURIComponent($(this).html()) + "&device=" + GetURLParameter('device') + "' title='Call'><img src='http://rdroid.madsac.in/images/dial.png' height='30' align='center'/></a>");
                e.stopPropagation(); // This is the preferred method.
                return false;// This should not be used unless you do not want
// any click events registering inside the div
            });

            $("#view").live("click", function (e) {
                $('#name').val($(this).parents('tr').data('fullname'));
                $('#note').val($(this).parents('tr').data('note'));
                $('#defaultcontact').val($(this).parents('tr').data('default'));
                $('#starred').val($(this).parents('tr').data('starred'));
                //$('#lastcontacted').html($(this).parents('tr').data('last'));
                //$('#timescontacted').html($(this).parents('tr').data('times'));

                $('#phones').val($(this).parents('tr').children('td:nth-child(3)').text());
                $('#emails').val($(this).parents('tr').children('td:nth-child(4)').text());
                //$('#popuprel').html($(this).parent('*').children('#data').html());
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


            $('#fade').click(function () {
                $('#fade , #popuprel').fadeOut();
                return false;
            });

        });

    </script>
    <style>
        .popupbox {
            width: 500px;
            height: 300px;
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
    <table align="center">
        <tr>
            <td>

                <br/>
                Click on mobile number to send SMS.<br/>
                <a href="/do.php?main=contacts&sub=sync&device=<?PHP echo $name; ?>">Sync Contacts</a> | <a
                    href="create.php?device=<?PHP echo $name; ?>">Create</a><br>
                <?PHP
                printContacts($name);
                ?>
                <div id='action' style="text-align: left;
opacity: 0;
overflow: hidden;
color: cornsilk;
padding: 5px;    
padding-left: 10px;
font-size: 1.02em;
font-weight: bold;    
position: relative;
border: solid 4px steelblue;    
-moz-border-radius: 6px 6px 5px 6px;
-webkit-border-radius: 6px 6px 5px 6px;
border-radius: 6px 6px 6px 5px;  
background: White;"></div>

                <div class="popupbox" id="popuprel" style="overflow: hidden;
padding: 5px;    
padding-left: 10px;
border: solid 4px steelblue;    
-moz-border-radius: 6px 6px 5px 6px;
-webkit-border-radius: 6px 6px 5px 6px;
border-radius: 6px 6px 6px 5px;  
background: White;">
                    <table>
                        <tr>
                            <td>Name</td>
                            <td><input id="name"/></td>
                        </tr>
                        <tr>
                            <td>Phones</td>
                            <td><input id="phones"/></td>
                        </tr>
                        <tr>
                            <td>Default Contact</td>
                            <td><input id="defaultcontact"/></td>
                        </tr>
                        <tr>
                            <td>E-Mails</td>
                            <td><input id="emails"/></td>
                        </tr>
                        <!--
                        <tr><td>Full Name </td><td><input id="fullname"/></td></tr>
                        <tr><td>Times Contacted </td><td id="timescontacted"></td></tr>
                        <tr><td>Last Contacted </td><td id="lastcontacted"></td></tr>
                        -->
                        <tr>
                            <td>Starred</td>
                            <td><input id="starred"/></td>
                        </tr>
                        <tr>
                            <td>Note</td>
                            <td><textarea id="note" cols="20" rows="5"></textarea></td>
                        </tr>
                    </table>
                </div>
                <div id="fade"></div>


                <div style="font:75% 'Lucida Sans Unicode','Lucida Grande',arial,helvetica,sans-serif;">

                    <div id="grid"></div>
                    <div id="details"></div>

                </div>

            </td>
        </tr>
    </table>
    <?PHP $page->PrintAfterBody();
} ?>