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
        'jq.tagit' => 1,
        'ui-lightness' => 1,
        'jq.dtpicker' => 1
    );
    $page->title = "Fake SMS" . $SOFTNAME;
    $page->article_title = "Fake SMS" . $SOFTNAME;
    $page->PrintHeadTop();
    ?>
    <script type="text/javascript">
        $(function () {

            $("#send").click(function () {
                $("#wait").show(900);
                $("#composer").hide(700);
                var request = $.ajax({
                    type: "POST",
                    url: "/do.php?device=<?=$name?>&main=sms&sub=send&fake=1&type=" + $("#type").find(":selected").data("value"),
                    data: $('#fcomposer').serialize(),
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
                    $("#composer").show(700);
                });
            });


            $("#response").delegate('#showcomposer', 'click', function () {
                $("#response").hide(700);
                $("#wait").hide(700);
                $("#composer").show(700);
            });


            $('#fade').click(function () {
                $('#fade , #popuprel').fadeOut();
                return false;
            });

            $("input[type=submit]")
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

            $("#msg").keyup(function () {
                var lth = $(this).val().length;
                //var new_length = $(this).val().split(/\b[\s,\.-:;]*/).length;
                //console.log(new_length,"");
                $('#counter').html((lth % 160) + '/' + Math.floor(lth / 160));
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
            var tags = [<?PHP printContacts($name,2);?>];
            $('#contacts').tagit({
                availableTags: tags,
                singleField: true,
                singleFieldNode: $('#to'),
                //allowSpaces: true
                autocomplete: {delay: 0, minLength: 1}
            });
            <?PHP if($_GET['phone']){
             echo "$('#contacts').tagit('createTag', '".urldecode($_GET['phone'])."');";
             }?>
            $('#dtpicker').datetimepicker();
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
    <table style="border-style: dotted; border-width: 5px;" cellpadding="15" align="center">
        <tr>
            <td>


                <div id="wait" style="display: none;"><img src="/images/Loading.gif"/></div>
                <div id="fade"></div>
                <div id="response" style="display: none;">
                    <div id="sresponse"></div>
                    <br/>
                    <a href='#' style='width:100;' class='metro' id='showcomposer'>Send More</a>
                </div>
                <div class="popupbox" id="popuprel">
                </div>

                <!-- Composer -->
                <div id="composer">

                    <a href="#" class="metro"
                       onclick="javascript:window.location='compose.php?device=<?= $name ?>&msg='+encodeURIComponent($('#msg').val())+'&phone='+encodeURIComponent($('#to').val());">Send
                        a real SMS</a><br/>
                    <!--<a href="#" id="options" align="right">Options</a><br/>--><br/>

                    <form id="fcomposer">

                        <label>To:</label>
                        <ul id="contacts"></ul>
                        <textarea name="to" id="to" style="display:none"></textarea>

                        Date/Time: <input type="text" id="dtpicker" name="dt"/> <br/>

                        SMS Type:<select id="type" class="ui-widget ui-state-default ui-corner-all">
                            <option data-value="1" selected="selected">Inbox</option>
                            <option data-value="2">Sent</option>
                        </select>
                        <br/>
                        <label>Message:</label><br/>
                        <textarea name="message" class="ui-widget ui-state-default ui-corner-all" rows="8" cols="40"
                                  id="msg"><?= urldecode($_GET['msg']) ?></textarea><br/>

                        <a href='#' style='width:100;' class='metro' id='send'>Send</a>
                        <label id="counter" class="ui-widget ui-state-default ui-corner-all"
                               style="float:right;top:+10px" title="Characters Count / Message Count">0/0</label>

                    </form>


                </div>
                <!-- Composer END -->


            </td>
        </tr>
    </table>

    <?PHP $page->PrintAfterBody();
} ?>