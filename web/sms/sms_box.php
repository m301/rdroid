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
        'jq.m-1.0' => 0,
        'jq.tagit' => 0,
        'ui-lightness' => 1,
        'jq-1.10-ui' => 1
    );
    $page->title = "SMS Box" . $SOFTNAME;
    $page->article_title = "SMS Box" . $SOFTNAME;
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

        .msg.out {
            margin-right: +100px;
            background: #BBFFFF;
            right: -50px;
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

        .msg.out:after {
            top: 16px; /* controls vertical position */
            right: -40px; /* value = - border-left-width - border-right-width */
            bottom: auto;
            left: auto;
            border-width: 10px 0 10px 50px;
            border-color: transparent #BBFFFF;
        }

        pre {
            white-space: pre-wrap; /* css-3 */
            white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
            white-space: -pre-wrap; /* Opera 4-6 */
            white-space: -o-pre-wrap; /* Opera 7 */
            word-wrap: break-word; /* Internet Explorer 5.5+ */
        }

        .thread-ui a div {
            background: #c73b1b url(/images/u_contact.png) 12px 12px no-repeat;
            padding-left: 70px;
        }
    </style>


    <script type="text/javascript">
    var contacts = {<?=printContacts($name,3);?>};
    function hideall() {
        $("pre#sms").each(function () {
            $(this).hide(1);
        });
        $('#smsbox').hide(1);
        $("#dlg").dialog("close");
        $(".thread-ui").hide(1);
    }
    function bdlgHide() {
        $('.ui-button:contains(Reply)').hide();
        $('.ui-button:contains(Forward)').hide();
        $('.ui-button:contains(Delete)').hide();
    }
    function bdlgShow() {
        $('.ui-button:contains(Reply)').show();
        $('.ui-button:contains(Forward)').show();
        $('.ui-button:contains(Delete)').show();
    }
    function distinctprop(selector, propibute) {
        var items = {};
        selector.each(function () {
            items[$(this).data(propibute)] = true;
        });
        var result = new Array();
        for (var i in items) {
            result.push(i);
        }
        return result;
    }
    function getThreadContact(thread) {
        var res = distinctprop($("pre[data-thread='" + thread + "']"), "no");
        return res.toString();
    }
    function getThreadContactName(thread) {
        var res = distinctprop($("pre[data-thread='" + thread + "']"), "no");
        for (var i = 0; i < res.length; i++) {
            var contact = contacts[res[i]];
            //console.log(contact);
            if (contact != undefined)
                res[i] = contact;
        }
        return res.toString();
    }

    function yesnodialog(button1, button2, element) {
        var btns = {};
        btns[button1] = function () {
            $("#wait").show(900);
            $("#main").hide(700);
            var request = $.ajax({
                type: "POST",
                url: "/do.php?device=<?=$name?>&main=sms&sub=delete",
                data: {id: element.children("#dsms").data("smsid")},
                timeout: 60000
            });
            request.done(function (msg) {
                $("#main").show(700);
                $("#wait").hide(700);
                $("#dlg").dialog("open").html("<div>" + msg + "</div>");
                bdlgHide();

            });
            request.fail(function (jqXHR, textStatus) {
                //alert( "Request failed. Reason : "+textStatus);
                $("#wait").hide(700);
                $("#main").show(700);
                $("#dlg").dialog("open").html("<div>An unknown error occured.Please try again.</div>");
                bdlgHide();
            });

            $(this).dialog("close");
        };
        btns[button2] = function () {
            $(this).dialog("close");
        };
        $("<div></div>").dialog({
            autoOpen: true,
            title: 'Delete ?',
            modal: true,
            buttons: btns
        });
    }


    $(document).ready(function () {
        $("#dlg").dialog({
            height: 300,
            width: 500,
            modal: true,
            autoOpen: false,
            hide: "explode",
            show: {effect: 'fade', duration: 1000},
            draggable: false,
            closeOnEscape: true,
            buttons: {
                'reply': {
                    priority: 'secondary',
                    text: 'Reply',
                    click: function () {
                        window.location.replace("/sms/compose.php?device=<?=$name?>&phone=" + encodeURI($(this).children("#dsms").data("no")));
                    }
                },
                'forward': {
                    priority: 'secondary',
                    text: 'Forward',
                    click: function () {
                        window.location.replace("/sms/compose.php?device=<?=$name?>&msg=" + encodeURI($(this).children("#dsms").children("#scont").html()));
                    }
                },
                'delete': {
                    priority: 'secondary',
                    text: 'Delete',
                    click: function () {
                        yesnodialog('Yes', 'No', $(this));
                    }
                }
            }
        });


        hideall();
        $("pre[data-type=1]").addClass("msg in").each(function (index) {
            $(this).append("<br/><br/><i style='float:right;font-size:1.0em;' id='mdt'>Date : " + $(this).first().data("dte") + "</i><br/>");
        });
        $("pre[data-type=2]").addClass("msg out").each(function (index) {
            $(this).append("<br/><br/><i style='float:left;font-size:1.0em;' id='mdt'>Date : " + $(this).first().data("dte") + "</i><br/>");
        });
        $("pre").sort(function (a, b) {
            return new Date($(a).data("dte")) > new Date($(b).data("dte"));
        }).each(function () {
            $("#smsbox").prepend(this);
        });

        $("#refresh,#sync").click(function (e) {
            $("#wait").show(900);
            $("#main").hide(700);
            if ($(this).text() == "Sync") {
                var rurl = "/webapi.php?device=<?=$name?>&action=smsbox&sync=1";
            } else {
                var rurl = "/webapi.php?device=<?=$name?>&action=smsbox";
            }
            var request = $.ajax({type: "POST", url: rurl, data: "", timeout: 60000});
            request.done(function (msg) {
                $("#wait").hide(700);
                $("#main").show(700);
                $("#smsbox").html(msg);
                $("#threads").click();
            });
            request.fail(function (jqXHR, textStatus) {
                console.log("Request failed. Reason : " + textStatus, "");
                $("#wait").hide(700);
                $("#main").show(700);
                $("#dlg").dialog("open").html("<div>An unknown error occured.Please try again.</div>");
                bdlgHide();
            });
        });

        $('#inbox,#sent,#all').click(function (e) {
            hideall();
            $('#smsbox').show(1);
            var selector = $("pre[data-type='" + $(this).prop("type") + "']");
            if ($(this).prop("id") == "all") {
                selector = $("pre#sms");
            }
            selector.each(function () {
                $(this).show(900);
                $(this).children('#mdt').show();
            });
            setTimeout(function () {
                $("#smsbox").animate({ scrollTop: $("#smsbox").prop("scrollHeight") }, "normal");
            }, 1000)

        });

        $('#threads').click(function () {
            hideall();
            $(".thread-ui").html("");
            var arr = distinctprop($("pre#sms"), "thread");
            for (var i = 0; i < arr.length; i++) {
                var tselec = $("pre[data-thread='" + arr[i] + "']");
                $(".thread-ui").append('<a href="#" data-thread="' + arr[i] + '"><h5>' + getThreadContactName(arr[i]) + '</h5><div>' + tselec.first().data("dte") + '<br/>' + getThreadContact(arr[i]) + '<br/>SMS : ' + tselec.length + '<br/>' + tselec.last().children("#scont").html().substr(0, 40) + '...</div></a>');
            }
            $(".thread-ui").show(900);
        }).click();

        $('.thread-ui').on('click', 'a', function () {
            $(".thread-ui").hide(800);
            hideall();
            $('#smsbox').show(1);
            var selector = $("pre[data-thread='" + $(this).first().data("thread") + "']");
            selector.each(function (index) {
                $(this).show(900);
                $(this).children('#mdt').show();
            });
            setTimeout(function () {
                $("#smsbox").animate({ scrollTop: $("#smsbox").prop("scrollHeight") }, "normal");
            }, 1000);
        });

        //var pressTimer;
        // var ths;
        $('#smsbox')
            //  .on('mouseup', '#sms', function(){
            //  clearTimeout(pressTimer)
            //  return false;
            //})
            //.on('mousedown', '#sms', function(){
            .on('click', '#sms', function () {
                var ths = $(this);
                bdlgShow();
                //pressTimer = window.setTimeout(function() {
                ths.children('#mdt').hide();
                $("#dlg").dialog("open")
                    .html("From : " + ths.first().data("no") + "<br/>Date : " + ths.first().data("dte") + "<pre id='dsms' data-smsid='" + ths.first().data("smsid") + "' data-no='" + ths.first().data("no") + "' >" + ths.html() + "</pre>")
                    .on("dialogclose", function (event, ui) {
                        ths.children('#mdt').show();
                    });
                //}
                //,600);
                return false;
            });

    });
    </script>
    <?PHP $page->PrintBetweenHeadAndBody(); ?>
    <div id="wait" style="display: none;">
        <table style="margin-left:auto;margin-right:auto;">
            <tr>
                <td>
                    <img src="/images/Loading.gif"/></td>
            </tr>
        </table>
    </div>
    <div id="main">
        <table style="margin-left:auto;margin-right:auto;">
            <tr>
                <td height="300">
                    <div id="smenu">
                        <a href='#' style='width:100;float:right;' class='metro' id='all' type="0">All</a><br/>
                        <a href='#' style='width:100;float:right;' class='metro' id='inbox' type="1">Inbox</a><br/>
                        <a href='#' style='width:100;float:right;' class='metro' id='sent' type="2">Sent</a><br/>
                        <a href='#' style='width:100;float:right;' class='metro' id='threads'>Threads</a><br/>
                        <a href='#' style='width:100;float:right;' class='metro' id='sync'>Sync</a><br/>
                        <a href='#' style='width:100;float:right;' class='metro' id='refresh'>Refresh</a><br/>
                    </div>

                </td>
                <td rowspan="2">
                    <div style="height:400px;width:500px;overflow-y:scroll;display:none;" id="smsbox"><?PHP
                        printSMSBox($name);
                        ?>
                    </div>
                    <section class="thread-ui" style="display:none;width:350px;">
                    </section>
                </td>
            </tr>
            <tr>
                <td></td>
            </tr>
        </table>
        <div id="dlg" title="SMS">
        </div>

    </div>


    <?PHP $page->PrintAfterBody();
} ?>