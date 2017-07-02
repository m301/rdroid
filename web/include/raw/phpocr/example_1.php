<?php
/**
 * Example, check images from file
 *
 * @author Andrey Kucherenko <kucherenko.andrey@gmail.com>
 * @package phpOCR
 */
include_once("config.php");
include_once("OCR.class.php");

//make new OCR object
$char = new OCR();

//Recognition process, check images from files
echo "<hr/><img src='M.png'/><br/>";
$res = $char->Recognition('M.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='M1.png'/><br/>";
$res = $char->Recognition('M1.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='z11.png'/><br/>";
$res = $char->Recognition('z11.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='z1.png'/><br/>";
$res = $char->Recognition('z1.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='Z.png'/><br/>";
$res = $char->Recognition('Z.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='B.png'/><br/>";
$res = $char->Recognition('B.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='B1.png'/><br/>";
$res = $char->Recognition('B1.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='W.png'/><br/>";
$res = $char->Recognition('W.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='W1.png'/><br/>";
$res = $char->Recognition('W1.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}


echo "<hr/><img src='W2.png'/><br/>";
$res = $char->Recognition('W2.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}

echo "<hr/><img src='plus1.png'/><br/>";
$res = $char->Recognition('plus1.png');
if ($res !== false) {
    echo "<b>" . $res->getName() . "</b>";
} else {
    echo "Not yet recognised.<br/>";
}


?>