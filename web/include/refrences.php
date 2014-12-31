<?PHP
/*
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
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
<script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
<script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
*/
$dn = $this->domain;
$this->external_js['code.jquery.com/jquery-1.9.1.min.js'] = $st['jquery'];
$this->external_js['code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js'] = $st['jq.m-1.0'];
$this->external_js['code.jquery.com/jquery-1.9.1.min.js'] = $st['jq-1.9'];
$this->external_js['code.jquery.com/jquery-1.6.2.js'] = $st['jq-1.6'];
$this->external_js['code.jquery.com/ui/1.10.2/jquery-ui.js'] = $st['jq-1.10-ui'];
$this->external_js['code.jquery.com/jquery-migrate-1.1.0.min.js'] = $st['jq-migrate'];
$this->external_js[$dn . '/kendo/js/kendo.web.min.js'] = $st['kendo.web.min'];
$this->external_js[$dn . '/script/js/jquery.multiselect.js'] = $st['jq.multiselect'];
$this->external_js[$dn . '/script/js/jquery.multiselect.filter.js'] = $st['jq.multiselect.filter'];
$this->external_js[$dn . '/script/js/jquery.toggleswitch.js'] = $st['jq.toggleswitch'];
$this->external_js[$dn . '/script/js/jquery.tagsinput.js'] = $st['jq.tagsinput'];
$this->external_js[$dn . '/script/js/jquery.tagit.js'] = $st['jq.tagit'];
$this->external_js[$dn . '/script/js/jquery.dtpicker.js'] = $st['jq.dtpicker'];
$this->external_js[$dn . '/script/js/jquery.galleriffic.js'] = $st['jq.galleriffic'];
$this->external_js[$dn . '/script/js/jquery.opacityrollover.js'] = $st['jq.opacityrollover'];
$this->external_js[$dn . '/script/js/jquery.filetree.js'] = $st['jq.filetree'];

$this->stylesheets['code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css'] = $st['jq.m-1.0'];
$this->stylesheets['ajax.googleapis.com/ajax/libs/jqueryui/1/themes/ui-lightness/jquery-ui.css'] = $st['ui-lightness'];
$this->stylesheets[$dn . '/rdroid/scripts/style.css'] = $st['rdroid'];
$this->stylesheets[$dn . '/kendo/styles/kendo.common.min.css'] = $st['kendo.common.min'];
$this->stylesheets[$dn . '/kendo/styles/kendo.default.min.css'] = $st['kendo.default.min'];
$this->stylesheets[$dn . '/script/css/style.css'] = !$st['no_stylesheet'];
$this->stylesheets[$dn . '/script/css/menu-style.css'] = !$st['no_stylesheet'];
$this->stylesheets[$dn . '/script/css/jquery.multiselect.css'] = $st['jq.multiselect'];
$this->stylesheets[$dn . '/script/css/jquery.multiselect.filter.css'] = $st['jq.multiselect.filter'];
$this->stylesheets[$dn . '/script/css/jquery.toggleswitch.css'] = $st['jq.toggleswitch'];
$this->stylesheets[$dn . '/script/css/jquery.toggleswitch.2.css'] = $st['jq.toggleswitch.2'];
$this->stylesheets[$dn . '/script/css/jquery.tagsinput.css'] = $st['jq.tagsinput'];
$this->stylesheets[$dn . '/script/css/jquery.tagit.css'] = $st['jq.tagit'];
$this->stylesheets[$dn . '/script/css/jquery.dtpicker.css'] = $st['jq.dtpicker'];
$this->stylesheets[$dn . '/script/css/bubbles.css'] = $st['bubbles'];
$this->stylesheets[$dn . '/script/css/jquery.galleriffic.css'] = $st['jq.galleriffic'];
$this->stylesheets[$dn . '/script/css/jquery.filetree.css'] = $st['jq.filetree'];
?>