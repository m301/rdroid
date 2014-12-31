<?PHP

error_reporting(E_STRICT);


function terminate_missing_variables($errno, $errstr, $errfile, $errline)
{
    if (($errno == E_NOTICE) and (strstr($errstr, "Undefined variable"))) die("$errstr in $errfile line $errline");

    return false; // Let the PHP error handler handle all the rest
}


class Template
{
    //I Will Define
    var $extra_head = "";
    var $extra_title = "";
    var $extra_article_title = "";
    var $body_content_footer = "";
    var $body_footer = "";
    var $body_content_header = "";
    var $body_header = "";
    var $server_root = "/home/mads8929/public_html/";
    var $top_menu;
    //User Defined
    var $title, $article_title, $is_mobile, $description, $keywords, $body_content, $head_content, $external_js, $stylesheets, $menu_path;
    //Constants
    var $domain, $page_name, $path, $settings, $scripts, $css, $url, $body;

    function Template($SERVER)
    {
        $host_names = explode(".", $_SERVER["SERVER_NAME"]);
        $this->domain = $host_names[count($host_names) - 2] . "." . $host_names[count($host_names) - 1];
        $this->page_name = basename($SERVER['PHP_SELF']);
        $this->path = str_replace($this->server_root, "", $SERVER['DOCUMENT_ROOT']);;
        $this->url = "http://" . $SERVER["SERVER_NAME"] . $SERVER["REQUEST_URI"];
        $domain = $this->domain;
        $this->top_menu = <<< Menu
    <table width=98%><tr><td><table style="float:right;"><tr><td>
<ul class="pureCssMenu pureCssMenum">
	<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/">Home</a></li>
	
	<li class="pureCssMenui"><a class="pureCssMenui" href="#"><span>Online Tools</span><![if gt IE 6]></a><![endif]><!--[if lte IE 6]><table><tr><td><![endif]-->
	<ul class="pureCssMenum">
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/online-notepad">Notepad</a></li>
                <li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/logger">Image Tracker</a></li>
                <li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/logger">IP Logger</a></li>
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/sms"><span>SMS</span><![if gt IE 6]></a><![endif]><!--[if lte IE 6]><table><tr><td><![endif]-->
		<ul class="pureCssMenum">
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/sms">SMS Sender [Desktop]</a></li>
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/sms/mobi.php">SMS Sender [Mobile]</a></li>
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/blog/api-usage">API</a></li>
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/sms/source/">PHP Script</a></li>
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/download/?category=sms">Applications</a></li>
			<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/sms/extract.php">Mobile Number Extractor</a></li>
		</ul>
		<!--[if lte IE 6]></td></tr></table></a><![endif]--></li>
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/remote-sms/partner_sites.php">Recieve SMS Online</a><li>
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/shorturl">Short Url</a><li>
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/whatismyip">WhatismyIP</a><li>
		<li class="pureCssMenui"><a class="pureCssMenui" href="http://xsms.in/">Send Fake SMS</a><li>
	</ul>
	<!--[if lte IE 6]></td></tr></table></a><![endif]--></li>
	<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/blog">Blog</a></li>
	<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/contact.php">Contact Us</a></li>
	<li class="pureCssMenui"><a class="pureCssMenui" href="http://$domain/about.php">About Us</a></li>
</ul></table><tr><td>
<hr style="height: 0px; border-top: 5px solid #333333; margin-top: -8px;" width=100%>
</table><center></center>

Menu;

    }

    function PrintPage()
    {
        if (!$this->is_mobile) {
            $this->PrintHeadTop();
            echo $this->head_content;
            $this->PrintBetweenHeadAndBody();
            echo $this->body_content;
            $this->PrintAfterBody();
        }
    }

    function PrintHeadTop()
    {
        $st = $this->settings;
        include("refrences.php");

        ?>
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
        <HEAD>
        <TITLE><?=

            $this->title;

            ?><?=

            !($st['no_extra_title']) ? $this->extra_title : ""

            ?></TITLE>
        <META NAME="description" CONTENT="<?=

        $this->description

        ?>">
        <META NAME="keywords" CONTENT="<?=

        $this->keywords

        ?>">
        <META NAME="robot" CONTENT="<?=

        ($st['no_robots_follow']) ? "no index,no follow" : "index,follow";

        ?>">
        <META NAME="revisit-after" CONTENT="<?=

        $st['revisit_after'] ? $st['revisit_after'] : "1";

        ?>">
        <?PHP
        foreach ($this->stylesheets as $key => $link) echo ($link) ? '<link rel="stylesheet" type="text/css" href="http://' . $key . '">' : "";
        foreach ($this->external_js as $key => $link) echo ($link) ? '<script type="text/javascript" src="http://' . $key . '"></script>' : "";
        ?>
        <?=
        !$st['no_analytics'] ? "<script type=\"text/javascript\">var _gaq = _gaq || [];  _gaq.push(['_setAccount', 'UA-37807496-1']);_gaq.push(['_setDomainName', '$domain']);  _gaq.push(['_trackPageview']);(function() {var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);})();</script>" : '';
        ?>
        <?=
        $st['fb_like'] ? '<script>(function(d, s, id){var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";fjs.parentNode.insertBefore(js, fjs);}(document, \'script\', \'facebook-jssdk\'));</script>' : "";
        ?>

    <?PHP

    }

function PrintBetweenHeadAndBody()
{
    $st = $this->settings;

    ?>

</head>
<body>
<?=

!($st['no_body_header']) ? $this->body_header : ""

?>
    <h1><?=

        $this->article_title;

        ?><?=

        !($st['no_extra_article_title']) ? $this->extra_title : ""

        ?></h1>
<?=

$this->top_menu

?>
<?=

$st['fb_like'] ? '<center><div class="fb-like" data-href="http://facebook.com/madsacsoft" data-send="true" data-width="450" data-show-faces="false"></div></center><hr width=96%>' : '';

?>
<?PHP

if (!$st['no_side_menu']) {
    echo '<div id="page"><div id="menu">';
    if (!($this->menupath)) include("menu.php");
    else  include($this->menupath . "menu.php");
    echo '</div><div id="content">';
}

?>
<?=

!($st['no_body_content_header']) ? $this->body_content_header : ""

?>
<?PHP

}

    function PrintAfterBody()
    {
        $st = $this->settings;

        ?>
        <?PHP

        if (!$st['no_side_menu']) {

            ?>
            <?=

            !($st['no_body_content_footer']) ? $this->body_content_footer : ""

            ?>
            </div>
        <?PHP

        }

        ?>
        </div>
        <?=

    !($st['no_body_footer']) ? $this->body_footer : ""

        ?>
        </body></html>
    <?PHP

    }
}

/*
* ini_set('include_path','.:/home/madsac/public_html/');
* include("include/templateClass.php");
* $website=new Template($_SERVER);
* $website->is_mobile=0;
* $website->settings=array(//SEO
* 'no_robots_follow'=>0,
* 'revisit_after'=>1,
* 
* 'no_extra_head'=>0,
* 'no_extra_title'=>0,
* 'no_extra_article_title'=>0,
* 'no_top_menu'=>0,
* 'no_side_menu'=>0,
* 'no_ads'=>0,
* 'no_analytics'=>1,
* 
* 'jquery'=>0,
* 'jquery_ui'=>0,
* 'fb_like'=>0
* );
*/

?>
