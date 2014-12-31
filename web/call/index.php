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
        'jq-1.9' => 1
    );
    $page->title = "Call Logs" . $SOFTNAME;
    $page->article_title = "Call Logs" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <script type="text/javascript">
        $(document).ready(function () {
            //window.location.replace("/sms/compose.php?device=<?=$name?>&phone="+ encodeURI($(this).children("#dsms").data("no")));


            //var pressTimer;
            //var ths;
            $('#main').on('click', '#call', function () {
                var num = $(this).children('.num').html();
                var id = $(this).data("pid");
                $("#popuprel").html('\
            \
            <section class="thread-ui" style="width:350px;">  \
            <a href="/call/dialer.php?device=<?=$name?>&phone=' + num + '" ><h5>Call ' + num + '</h5>\
            <div>Opens dialer with number ' + num + '</div>\
            <a href="/sms/compose.php?device=<?=$name?>&phone=' + num + '"><h5>Send Message</h5>\
            <div>Opens SMS composer with number ' + num + ' </div>\
            <a href="/do.php?device=<?=$name?>&main=call&sub=clearlogs&type=_ID&value=' + id + '"><h5>Delete</h5>\
            <div>Delete selected call log.</div>\
            <section>\
            ').css({ 'width': '360px', 'height': '250px'}).fadeIn();
                $('body').append('<div id="fade"></div>');
                $('#fade').css({'filter': 'alpha(opacity=80)'}).fadeIn();
                var popuptopmargin = ($('#popuprel').height() + 10) / 2;
                var popupleftmargin = ($('#popuprel').width() + 10) / 2;
                $('#popuprel').css({
                    'margin-top': -popuptopmargin,
                    'margin-left': -popupleftmargin
                });


            }).on('click', '#sync', function () {
                $("#popuprel").html('\
            \
            <form action="/do.php?device=<?=$name?>&main=call&sub=sync" method="POST">\
            Number of Logs :<input type="number" name="count" value="100"/><br/>\
            <input type="submit" style="margin-left:auto;margin-right:auto;" class="metro" value="Ok Sync"/><br />\
            </form>\
            \
            ').css({ 'width': '200px', 'height': '100px'}).fadeIn();
                $('body').append('<div id="fade"></div>');
                $('#fade').css({'filter': 'alpha(opacity=80)', }).fadeIn();
                var popuptopmargin = ($('#popuprel').height() + 10) / 2;
                var popupleftmargin = ($('#popuprel').width() + 10) / 2;
                $('#popuprel').css({
                    'margin-top': -popuptopmargin,
                    'margin-left': -popupleftmargin
                });

                return false;
            });

            /*
             .on('mouseup', '#call', function(){
             clearTimeout(pressTimer)
             return false;
             })
             .on('mousedown', '#call', function(){
             var ths=$(this);
             pressTimer = window.setTimeout(function() {
             console.log(ths.children('.num').html())

             },600);
             return false;
             });
             */

            $('tr#call').hover(function () {
                $(this).contents('td:first').css({'border-left': '1px solid red', 'border-top': '1px solid red', 'border-bottom': '1px solid red'});
                $(this).contents('td:last').css({'border-right': '1px solid red', 'border-top': '1px solid red', 'border-bottom': '1px solid red'});

            }, function () {
                $(this).contents('td:first').css({'border-left': '1px solid #cbd2d8', 'border-top': '1px solid #f1f8fe', 'border-bottom': '1px solid #cbd2d8'});
                $(this).contents('td:last').css({'border-right': '1px solid #cbd2d8', 'border-top': '1px solid #f1f8fe', 'border-bottom': '1px solid #cbd2d8'});
            });

            $('#fade').click(function () {
                $('#fade , #popuprel').fadeOut();
                return false;
            });
        });

    </script>
    <style>.popupbox {

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

        .thread-ui a div {
            background: #c73b1b;
            padding-left: 10px;
            padding-top: 30px;
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
    <table style="margin-left:auto;margin-right:auto;">
        <tr>
            <td>

                <div id="wait" style="display: none;">
                    <img src="/images/Loading.gif"/>
                </div>

                <div id="main">
                    <a href='#' class='metro' id='sync'>Sync</a><br/>
                    <?PHP
                    printCallLogs($name);?>
                </div>


            </td>
        </tr>
    </table>
    <div class="popupbox" id="popuprel" style="overflow: hidden;
padding: 5px;    
padding-left: 10px;
border: solid 4px steelblue;    
-moz-border-radius: 6px 6px 5px 6px;
-webkit-border-radius: 6px 6px 5px 6px;
border-radius: 6px 6px 6px 5px;  
background: White;
margin-left:auto;
margin-right:auto;"></div>
    <div id="fade"></div>
    <?PHP $page->PrintAfterBody();
} ?>