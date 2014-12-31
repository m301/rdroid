<?php
/**
 * Example, learn OCR to recognition letters
 *
 * @author Andrey Kucherenko <kucherenko.andrey@gmail.com>
 * @package phpOCR
 */

include_once("config.php");
include_once("OCR.class.php");

//make new OCR object
$char = new OCR();

//method draw letter & learn system
$char->Learn("W");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "W"<br>';


//method draw letter & learn system
$char->Learn("z");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "z"<br>';

//method draw letter & learn system
$char->Learn("Z");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "Z"<br>';

//method draw letter & learn system
$char->Learn("M");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "M"<br>';

//method draw letter & learn system
$char->Learn("B");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "B"<br>';

//learn system to understand symbol "plus"
$char->LearnFromImage("plus.png", "plus");
//save sarialized object to file
$char->saveResult();
echo 'Save information about "plus"<br/><img src="plus.png"/>';




?>