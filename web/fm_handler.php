<?php
include("functions.php");
$_POST['dir'] = urldecode($_POST['dir']);
//print_r($_POST);
$f = new fs(getFilesList("galaxy"));
//$f->scandir("sdcard/My/");
//echo "<pre>";
//array_keys($f->scandir("sdcard/My/"));
//echo "</pre>";
$dir = $_POST['dir'];
//$dir="/";
$rDir = $f->raw_scandir($dir);
//print_r($rDir);
//if( $f->file_exists($rDir,$dir) ) {

$files = $f->getFiles($rDir);
natcasesort($files);
//print_r($files);
if (count($files) > 2) { /* The 2 accounts for . and .. */

    echo "<ul class=\"jqueryFileTree\" style=\"display: none;\">";
    // All dirs
    foreach ($files as $file) {
        //if( file_exists($root . $_POST['dir'] . $file) && $file != '.' && $file != '..' && is_dir($root . $_POST['dir'] . $file) ) {
        if ($f->is_dir($rDir, $file)) {
            echo "<li class=\"directory collapsed\"><a href=\"#\" rel=\"" . htmlentities($_POST['dir'] . $file) . "/\">" . htmlentities($file) . "</a></li>";
        }
    }
    // All files
    foreach ($files as $file) {
        //		if( file_exists($root . $_POST['dir'] . $file) && $file != '.' && $file != '..' && !is_dir($root . $_POST['dir'] . $file) ) {
        if (!$f->is_dir($rDir, $file)) {
            $ext = preg_replace('/^.*\./', '', $file);
            echo "<li class=\"file ext_$ext\"><a href=\"#\" rel=\"" . htmlentities($_POST['dir'] . $file) . "\">" . htmlentities($file) . "</a></li>";
        }
    }
    echo "</ul>";
}
// */
//}


class fs
{
    var $raw_data;
    var $date;
    var $root;

    function fs($data)
    {
        $ar = explode("|**DATA**|", $data);
        $this->raw_data = $ar[2];
        $this->date = $ar[0];
        $this->root = $ar[1];
    }

    public function getFiles($list)
    {
        foreach ($list as $key => $item)
            $lst[] = $key;
        return $lst;
    }

    public function raw_scandir($dir)
    {
        $list = array();
        $data = $this->raw_data;
        if ($dir == "/") {
            $list = $this->tScanDir($data, ">");
        } else {
            $dirs = explode("/", $dir);
            for ($i = 1; $i < count($dirs); $i++) {
                $cur = $dirs[$i];
                $list = $this->tScanDir($data, $this->getDeli($i));
                //echo "<pre>";print_r($list);echo "</pre>";
                // if (array_key_exists($cur, $list)){
                $data = $list[$cur];
                //echo "found";
                // }else{
                // echo "$i Not found!" ;
                //  }
            }
        }
        //$list=$this->tScanDir($data,$this->getDeli($i+1));
        return $list;
    }

    public function tScanDir($data, $deli)
    {
        $lst = array();
        $arr = explode("|" . $deli . "|", $data);
        array_shift($arr);
        foreach ($arr as $ar) {
            $ar = explode("<", $ar, 2);
            if (substr_count($ar[0], "|") > 0)
                $ar = explode("|", $ar[0], 2);
            $lst[$ar[0]] = $ar[1];
        }
        //print_r($lst);
        return $lst;
    }

    function getDeli($count)
    {
        $str = "";
        while ($count) {
            $str = $str . ">";
            $count--;
        }
        //echo $str;
        return $str;
    }

    public function is_dir($list, $dir)
    {
        //echo substr_count($list[$dir],"|");
        if (substr_count($list[$dir], "|") == 1)
            return 0;
        return 1;
    }

    public function file_exists($list, $dir)
    {
        return array_key_exists($dir, $list);
    }
}

?>