<?php
/**
 * phpOCR system
 * Simple Optical Character Recognition system, can recognition black&while images,
 * for working with this system must learn that
 *
 * @author Andrey Kucherenko <kucherenko.andrey@gmail.com>
 * @package phpOCR
 */

/**
 * Char class
 */
class char
{
    private $width;
    private $height;
    private $imageInfo = array();
    private $name;
    private $time;

    public function getName()
    {
        return $this->name;
    }

    public function setName($char)
    {
        $this->name = $char;
    }

    public function getTime()
    {
        return $this->time;
    }

    public function setTime($time)
    {
        $this->time = $time;
    }

    public function getWidth()
    {
        return $this->width;
    }

    public function setWidth($value)
    {
        $this->width = $value;
    }

    public function getHeight()
    {
        return $this->height;
    }

    public function setHeight($value)
    {
        $this->height = $value;
    }

    public function getType($x, $y)
    {
        $xCorners = $this->getValuesByParam("x", $x);
        $corner = $this->getValuesByParam("y", $y, $xCorners);
        if (isset($corner[0]['type'])) {
            return $corner[0]['type'];
        } else {
            return false;
        }
    }

    public function getValuesByParam($paramName, $value, $array = null)
    {
        if (is_null($array)) {
            $array = $this->imageInfo;
        }

        if ($array === false) {
            return false;
        }

        $res = array();

        if (count($this->imageInfo)) {
            foreach ($this->imageInfo as $vals) {
                if ($vals[$paramName] == $value) {
                    $res[] = $value;
                }
            }
        }
        if (count($res)) {
            return $res;
        } else {
            return false;
        }

    }

    public function setCorner($x, $y, $type)
    {
        $j = count($this->imageInfo);
        $this->imageInfo[$j]["x"] = $x;
        $this->imageInfo[$j]["y"] = $y;
        $this->imageInfo[$j]["type"] = $type;
    }

    public function getImageInfo()
    {
        return $this->imageInfo;
    }
}

?>