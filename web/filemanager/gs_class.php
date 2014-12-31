<?php
include("../functions.php");

class GSFileSystemFileStorage
{
    var $raw_data;
    var $date;
    var $root;
    var $buf_list, $buf_dir, $buf_file;
    var $name;

    public function GSFileSystemFileStorage($name)
    {
        $this->name = $name;
        $ar = explode("|**DATA**|", urldecode(strd(getFilesList($this->name))));
        //print_r($ar);
        //echo count($ar);
        $this->raw_data = $ar[2];
        $this->date = $ar[0];
        $this->root = $ar[1];
    }


    public function is_dir($file)
    {
        //echo $file;
        $this->createBuffer($file);
        if (substr_count($this->buf_list[$this->getFileName($file)], "|") == 1)
            return 0;
        return 1;
    }

    public function createBuffer($dir)
    {
        // echo $dir.":";
        if (!($this->buf_dir == $dir)) {
            $this->buf_dir = $dir;
            $this->buf_list = $this->raw_scandir($this->buf_dir);
            $this->buf_file = $this->getFiles($this->buf_list);
        }
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
                //if (array_key_exists($cur, $list))
                @$data = $list[$cur];
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

    public function getFiles($list)
    {
        $lst[] = '.';
        $lst[] = '..';
        foreach ($list as $key => $item)
            $lst[] = $key;
        return $lst;
    }

    public function getFileName($dir)
    {
        $ar = explode("/", $dir);
        return $ar[count($ar) - 1];
    }

    public function file_exists($file)
    {
        //  echo $file."|>>";
        //if($file=="/")
        return 1;
        ///  $arr=explode('/',$file);
        //  array_pop($arr);
        //  $file=implode('/',$arr);
        //  $this->createBuffer($file);
        //  return in_array($this->getFileName($file),$this->buf_file);
    }

    public function scandir($file)
    {
        $this->createBuffer($file);
        return $this->buf_file;
    }

    public function filesize($file)
    {
        $this->createBuffer($file);
        $arr = explode("|", $this->buf_list[$this->getFileName($file)]);
        return $arr[1];
    }

    public function deleteFile($file)
    {
        return doAct($this->name, 'fdelete' . substr($file, 7));
    }

    public function move_uploaded_file($data, $dir)
    {
        //if ($fname['error'] == UPLOAD_ERR_OK ) {
        //&& is_uploaded_file($fname['tmp_name'])
        $data = file_get_contents("php://input");
        error_log($data, 0);
        $dt = date('Y/m/d H:i');
        $query = "INSERT INTO `fileBuff` SET (`name`,`uid`,`owner`,`file`,`dt`,`downloads`) VALUES ('" . stre($dir) . "','" . rStr() . "','" . $this->name . "','$data','$dt','0');";
        error_log($query, 0);
        $result = mysql_query($query);
        error_log($result, 0);
        return $result;
        //}
        //return 0;
        //return @move_uploaded_file($from, $to);
    }

    public function deleteDirectory($dirname)
    {
        return doAct($this->name, 'fddelete' . substr($dirname, 7));
    }

    public function makeDirectory($dirname)
    {
        return doAct($this->name, 'fmk' . substr($dirname, 7));
    }

    public function makeFile($filename)
    {
        if ($handle = fopen($filename, 'w')) {
            fclose($handle);
            return true;
        }
        return false;
    }

    public function filemtime($file)
    {
        $this->createBuffer($file);
        $arr = explode("|", $this->buf_list[$this->getFileName($file)]);
        return $arr[0];
    }

    public function copyFile($from, $to)
    {
        return doAct($this->name, 'fcopy' . substr($from, 7) . '|MAD|' . substr($to, 7));
    }

    public function copyDir($src, $dst)
    {
        //$arr=explode('/',$dst);
        //array_pop($arr);
        //$dst=implode('/',$arr);
        return doAct($this->name, 'fdcopy' . substr($src, 7) . '|MAD|' . substr($dst, 7));
    }

    public function renameItem($from, $to)
    {
        return doAct($this->name, 'frename' . substr($from, 7) . '|MAD|' . substr($to, 7));
    }

    public function dlfile_exists($dir)
    {
        $query = "SELECT `name` FROM `fileBuff` WHERE `name`='" . stre($dir) . "' AND `owner`='" . $this->name . "';";
        return b_rows(mysql_query($query));
    }

    public function requestFile($dir)
    {
        doAct($this->name, "getfile" . substr(trim($dir), 7));
        echo "The file you requested is not found in our servers.It will be available when client has uploaded it.Request has been sent to start uploading.";
    }
    /*
    
    public function readFile($filename){
		return file_get_contents($filename);
	}

	public function writefile($filename, $content){
		return @file_put_contents($filename, $content);
	}
    public function parseImage($ext,$img,$file = null){
		$result = false;
		switch($ext){
			case 'png':
				$result = imagepng($img,($file != null ? $file : ''));
			break;
			case 'jpeg':
				$result = imagejpeg($img,($file ? $file : ''),90);
			break;
			case 'jpg':
				$result = imagejpeg($img,($file ? $file : ''),90);
			break;
			case 'gif':
				$result = imagegif($img,($file ? $file : ''));
			break;
		}
		return $result;
	}
	
	 public function imagecreatefrompng($src) {
	     return imagecreatefrompng($src);	
	 }
	 
	 public function imagecreatefromjpeg($src){
		 return imagecreatefromjpeg($src);
	 }
	 
	 public function imagecreatefromgif($src){
	 	 return imagecreatefromgif($src);
	 }
     */

}