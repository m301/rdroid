<?
/**
 * phpOCR system is a simple Optical Character Recognition system, it can recognise black&while images.
 * It has to be taught in order it to work in a light way.
 * In order to recognise the images "corner"'s algorithm is used.
 *
 * @author Andrey Kucherenko <kucherenko.andrey@gmail.com>
 * @package phpOCR
 */
include_once("char.object.php");

/**
 * Main OCR class
 *
 */
class OCR
{

    private $image;
    private $charObject;
    private $width;
    private $height;
    private $name;


    /**
     * Learn system to understand letter $str
     *
     * @param string $str
     */
    public function Learn($str)
    {
        $this->clear();
        $this->name = $str;
        $this->createImage();
        $this->charObject->setName($str);
        $this->charObject->setWidth($this->width);
        $this->charObject->setHeight($this->height);

        $this->makeCharObject();

    }

    /**
     * Clean up OCR object
     *
     */
    private function clear()
    {
        unset($this->image);
        unset($this->charObject);
        unset($this->name);
        $this->charObject = new char();
    }

    /**
     * Make image for learning
     *
     */
    private function createImage()
    {
        $im = imagecreatetruecolor(100, 100);

        $white = imagecolorallocate($im, 255, 255, 255);
        $black = imagecolorallocate($im, 0, 0, 0);

        imagechar($im, 2, 1, 1, $this->name, $white);

        $this->getSize($im);

        $this->image = imagecreatetruecolor($this->width, $this->height);

        imagecopy($this->image, $im, 0, 0, 0, 0, $this->width, $this->height);
    }

    /**
     * get image size
     *
     * @param image $im
     */
    private function getSize($im)
    {
        $w = 0;
        $h = 0;
        for ($x = 0; $x < 100; $x++) {
            for ($y = 0; $y < 100; $y++) {
                if (($this->getBWcolor($im, $x, $y) == COLOR_WHITE) && ($w <= $x)) {
                    $w = $x;
                }
                if (($this->getBWcolor($im, $x, $y) == COLOR_WHITE) && ($h <= $y)) {
                    $h = $y;
                }
            }
        }
        if (($w > 0) && ($h > 0)) {
            $w += 2;
            $h += 2;
            $this->width = $w;
            $this->height = $h;
        }
    }

    /**
     * Get pixels color, understund only black or white pixels
     *
     * @param image $im
     * @param int $x
     * @param int $y
     * @return int
     */
    private function getBWcolor($im, $x, $y)
    {
        $rgb = @ImageColorAt($im, $x, $y);
        $r = ($rgb >> 16) & 0xFF;
        $g = ($rgb >> 8) & 0xFF;
        $b = $rgb & 0xFF;
        if (($r == 255) && ($g == 255) && ($b == 255)) {
            return COLOR_WHITE;
        }
        return COLOR_BLACK;
    }

    /**
     * Create char object from image object
     *
     */
    private function makeCharObject()
    {
        for ($x = 0; $x < $this->width; $x++) {
            for ($y = 0; $y < $this->height; $y++) {
                $type = $this->getCornerType($this->image, $x, $y);
                if ($type !== false) {
                    $this->charObject->setCorner($x, $y, $type);
                }
            }
        }
    }

    /**
     * Get type of corners
     *
     * @param image $im
     * @param int $x
     * @param int $y
     * @return int
     */
    private function getCornerType($im, $x, $y)
    {
        $p11 = $this->getBWcolor($im, $x, $y);
        $p21 = $this->getBWcolor($im, $x + 1, $y);
        $p12 = $this->getBWcolor($im, $x, $y + 1);
        $p22 = $this->getBWcolor($im, $x + 1, $y + 1);

        $sum = $p11 + $p12 + $p21 + $p22;

        if ($sum % 2 == 0) {
            return false;
        }

        if (($p11 == COLOR_BLACK) && ($p21 == COLOR_WHITE) && ($p12 == COLOR_WHITE) && ($p22 == COLOR_WHITE)) {
            return CORNER_BP_LEFT_TOP;
        }

        if (($p11 == COLOR_WHITE) && ($p21 == COLOR_BLACK) && ($p12 == COLOR_WHITE) && ($p22 == COLOR_WHITE)) {
            return CORNER_BP_RIGHT_TOP;
        }

        if (($p11 == COLOR_WHITE) && ($p21 == COLOR_WHITE) && ($p12 == COLOR_BLACK) && ($p22 == COLOR_WHITE)) {
            return CORNER_BP_LEFT_BOTTOM;
        }

        if (($p11 == COLOR_WHITE) && ($p21 == COLOR_WHITE) && ($p12 == COLOR_WHITE) && ($p22 == COLOR_BLACK)) {
            return CORNER_BP_RIGHT_BOTTOM;
        }

        if (($p11 == COLOR_WHITE) && ($p21 == COLOR_BLACK) && ($p12 == COLOR_BLACK) && ($p22 == COLOR_BLACK)) {
            return CORNER_WP_LEFT_TOP;
        }

        if (($p11 == COLOR_BLACK) && ($p21 == COLOR_WHITE) && ($p12 == COLOR_BLACK) && ($p22 == COLOR_BLACK)) {
            return CORNER_WP_RIGHT_TOP;
        }

        if (($p11 == COLOR_BLACK) && ($p21 == COLOR_BLACK) && ($p12 == COLOR_WHITE) && ($p22 == COLOR_BLACK)) {
            return CORNER_WP_LEFT_BOTTOM;
        }

        if (($p11 == COLOR_BLACK) && ($p21 == COLOR_BLACK) && ($p12 == COLOR_BLACK) && ($p22 == COLOR_WHITE)) {
            return CORNER_WP_RIGHT_BOTTOM;
        }

    }

    /**
     * Learn systems to understand symbols form image
     *
     * @param string $path
     * @param string $name
     */
    public function LearnFromImage($path, $name)
    {
        $this->clear();
        $this->name = $name;

        $im = $this->createImageFromFile($path);
        $this->image = imagecreatetruecolor($this->width, $this->height);
        imagecopy($this->image, $im, 0, 0, 0, 0, $this->width, $this->height);

        $this->charObject->setName($name);
        $this->charObject->setWidth($this->width);
        $this->charObject->setHeight($this->height);

        $this->makeCharObject();

    }

    /**
     * create image for recognition
     *
     * @param string $img_file
     * @return image
     */
    private function createImageFromFile($img_file)
    {
        $img = 0;
        $img_sz = getimagesize($img_file);
        switch ($img_sz[2]) {
            case 1:
                $img = ImageCreateFromGif($img_file);
                break;
            case 2:
                $img = ImageCreateFromJpeg($img_file);
                break;
            case 3:
                $img = ImageCreateFromPng($img_file);
                break;
        }
        $this->width = $img_sz[0];
        $this->height = $img_sz[1];
        return $img;
    }

    /**
     * Recognition method
     *
     * @param string $path
     * @return object
     */
    public function Recognition($path)
    {
        $this->clear();
        $im = $this->createImageFromFile($path);
        $this->image = imagecreatetruecolor($this->width, $this->height);
        imagecopy($this->image, $im, 0, 0, 0, 0, $this->width, $this->height);

        $this->charObject->setWidth($this->width);
        $this->charObject->setHeight($this->height);
        $this->makeCharObject();

        $d = dir("./storage/");
        while (false !== ($entry = $d->read())) {
            if ((eregi(".char$", $entry))) {
                if ($this->checkCharObject(implode('', file("./storage/" . $entry)))) {
                    return unserialize(implode('', file("./storage/" . $entry)));
                }
            }
        }
        $d->close();

        return false;
    }

    /**
     * Comare charObjects to congruous
     *
     * @param string $str
     * @param boolean $print
     * @return boolean
     */
    private function checkCharObject($str, $print = true)
    {
        $m = 1;
        $char = unserialize($str);

        if ($print) {
            echo "Check \"" . $char->getName() . "\"<br/>";
        }
        $currentImagesInfo = $char->getImageInfo();
        $imageInfo = $this->charObject->getImageInfo();

        if (count($imageInfo)) {
            $m = 0;
            foreach ($imageInfo as $key => $value) {

                if ((isset($currentImagesInfo[$key]['type'])) && (($currentImagesInfo[$key]['type'] != $value['type']) && ($currentImagesInfo[$key]['type'] != OCR::getNegative($value['type'])))) {
                    $m++;
                }
            }
        }

        if ($print) {
            echo "We have " . $m . " errors <br/>";
        }
        if ($m == 0) {
            if ($print) {
                echo "It is \"" . $char->getName() . "\"<br/>";
            }
            return true;
        } else {
            if ($print) {
                echo "It is not \"" . $char->getName() . "\"<br/>";
            }
            return false;
        }
    }

    /**
     * Get inverce corner
     *
     * @param int $type
     * @return int
     */
    static private function getNegative($type)
    {
        if ($type > 4) {
            return $type - 4;
        } else {
            return $type + 4;
        }
    }

    /**
     * Save chars object to file in storage
     *
     */
    public function saveResult()
    {
        $time = time();
        $this->charObject->setTime($time);
        $fl = fopen("./storage/" . $this->name . md5($this->name) . ".char", "w");
        fwrite($fl, serialize($this->charObject));
        fclose($fl);
    }

    /**
     * Output image
     *
     */
    public function outputImage()
    {
        header('Content-type: image/png');
        imagepng($this->image);
    }

}

?>