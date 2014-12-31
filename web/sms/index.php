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
        'jq-migrate' => 1,
        'jq-1.10-ui' => 0,
        'jq.tagit' => 0,
        'ui-lightness' => 1,
    );
    $page->title = "SMS" . $SOFTNAME;
    $page->article_title = "SMS" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <style>
        .msg {
            position: relative;
            padding: 15px;
            margin: 1em 0 3em;
            color: #000;
            background: #f3961c; /* default background for browsers without gradient support */
            /* css3 */
            background: -webkit-gradient(linear, 0 0, 0 100%, from(#f9d835), to(#f3961c));
            background: -moz-linear-gradient(#f9d835, #f3961c);
            background: -o-linear-gradient(#f9d835, #f3961c);
            background: linear-gradient(#f9d835, #f3961c);
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            border-radius: 10px;
        }

        .msg.in {
            margin-left: +100px;
            background: #e6e6fa;
            left: -50px;
        }

        .msg:after {
            content: "";
            position: absolute;
            bottom: -15px; /* value = - border-top-width - border-bottom-width */
            left: +50px; /* controls horizontal position */
            border-width: 15px 15px 0; /* vary these values to change the angle of the vertex */
            border-style: solid;
            border-color: #1C86EE transparent;
            /* reduce the damage in FF3.0 */
            display: block;
            width: 0;
        }

        .msg.in:after {
            top: 16px; /* controls vertical position */
            left: -40px; /* value = - border-left-width - border-right-width */
            bottom: auto;
            border-width: 10px 50px 10px 0;
            border-color: transparent #e6e6fa;
        }

        pre {
            white-space: pre-wrap; /* css-3 */
            white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
            white-space: -pre-wrap; /* Opera 4-6 */
            white-space: -o-pre-wrap; /* Opera 7 */
            word-wrap: break-word; /* Internet Explorer 5.5+ */
        }

        .thread-ui a div {
            background: #c73b1b;
            margin-left: auto;
            margin-right: auto;
            padding-top: 20px;
        }

    </style>
    <script type="text/javascript">
        var contacts = {<?=printContacts($name,3);?>};
        function getContactName(number) {
            var contact = contacts[number];
            if (contact != undefined)
                return contact;
            return "Unknown [" + number + "]";
        }
        $("#phone").live("click", function (e) {
            $('#action').css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 400);
//$("#action").width(70);
            $("#action").offset({left: e.pageX + 30, top: e.pageY - 70});
            $("#action").html("" + getContactName($(this).html()) + "");
            e.stopPropagation(); // This is the preferred method.
            return false;// This should not be used unless you do not want
// any click events registering inside the div
        });
    </script>
    <?PHP $page->PrintBetweenHeadAndBody(); ?>

    <div id='action' style="text-align: left;
opacity: 0;
padding: 5px;    
padding-left: 10px;
font-size: 1.02em;
font-weight: bold;    
position: absolute;
z-index:99;
border: solid 4px steelblue;    
-moz-border-radius: 6px 6px 5px 6px;
-webkit-border-radius: 6px 6px 5px 6px;
border-radius: 6px 6px 6px 5px;  
background: White;
"></div>
    <section class="thread-ui" style="width:350px;margin-left:auto;margin-right:auto;">
        <a href="compose.php?device=<?= $name ?>"><h5>Compose</h5>

            <div><br/>Send a new message...</div>
        </a>
        <a href="fakesms.php?device=<?= $name ?>"><h5>Fake SMS</h5>

            <div><br/>Create a fake sms in your inbox</b></div>
        </a>
        <a href="sms_box.php?device=<?= $name ?>"><h5>SMS Box</h5>

            <div><br/>Manage your SMS...</b></div>
        </a>

    </section>
    <table align=center width=50%>
        <tr>
            <td>
                Latest SMS :
                <?PHP
                //echo "<a href='sms_box.php?device=$name'>SMS Box </a><br/>";
                printLatestSMS($name);
                ?>
            </td>
        </tr>
    </table>
    <?PHP $page->PrintAfterBody();
} ?>