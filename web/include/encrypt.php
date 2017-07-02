<?php

function en($str)
{
    return base64_encode($str);
}

function de($str)
{
    return base64_decode($str);
}

?>