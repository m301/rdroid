<?PHP
/* MAD Don't forget to close </head> */
//   Sample Code

/*
$atop=1; //Add top.php
$atitle="Your Title"; //Title of Article
$aaddtitle=1; //Add add-title.php
$notice=1;
$menupath="path of page from root";
$bottom=1;
$footer=1;

body_top($atop,$atitle,$aaddtitle,$topmenu,$notice,$menupath,$fblike,$mobile);
//rest content here
body_bottom($bottom,$footer,$mobile);
*/
//End Of Sample Code

function body_top($top, $title, $addtitle, $topmenu, $notice, $menupath, $fblike, $mobile)
{
    $ipath = "body/";
    if ($mobile == 0) {
        ?>
        <?PHP if ($top) include($ipath . "top.php"); ?>
        <body>
        <h1><?PHP echo $title; ?><?PHP if ($addtitle) include($ipath . "add-title.php"); ?></h1>
        <?PHP if ($topmenu) include($ipath . "topmenu.php"); ?>
        <center><?PHP if ($fblike) include($ipath . "fblike.php"); ?></center>
        <?PHP if ($notice) include($ipath . "notice.php"); ?>
        <hr width=96%>

        <?PHP if ($menupath) {
            echo '<div id="page">';
            echo '<div id="menu">';
            include($ipath . "sidebar-top.php");
            if (strlen($menupath) <= 1)
                include("menu.php");
            else
                include($menupath . "menu.php");

            include($ipath . "sidebar-bottom.php");
            echo "</div>";
            echo '<div id="content"><div class="article">';
        }

        ?>
    <?PHP
    }
}

?>

<?PHP
function body_bottom($bottom, $footer, $mobile)
{
    $ipath = "body/";
    if ($mobile == 0) {
        ?>
        </div>
        <?PHP if ($footer) include($ipath . "footer.php"); ?>
        </div>
        </div>
        <?PHP if ($bottom) include($ipath . "bottom.php"); ?>
        </body>
        </html>
    <?PHP
    }
}

?>