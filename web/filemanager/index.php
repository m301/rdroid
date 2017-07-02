<html>
<head>
    <title>File Manager</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link href="http://madsac.in/script/gs/css/jquery-ui.css" rel="stylesheet" type="text/css" media="screen"/>
    <script src="http://madsac.in/script/gs/jquery.min.js" type="text/javascript"></script>
    <script src="http://madsac.in/script/gs/jquery-ui-min.js" type="text/javascript"></script>
    <script src="http://madsac.in/script/gs/gsFileManager.js" type="text/javascript"></script>
    <script src="http://madsac.in/script/gs/jquery.form.js" type="text/javascript"></script>
    <!--	<script src="http://madsac.in/script/gs/jquery.Jcrop.js" type="text/javascript"></script>
        -->
    <link href="http://madsac.in/script/gs/gsFileManager.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--	<link href="http://madsac.in/script/gs/jquery.Jcrop.css" rel="stylesheet" type="text/css" media="screen" />
        -->
    <style>
        body {
            height: 100%;
            width: 100%;
        }

        html {
            height: 85%;
            width: 99%;
        }

        .metro {
            display: inline-block;
            padding: 10px;
            margin: 10px;
            background: #08C;

            /* Font styles */
            color: white;
            font-weight: bold;
            text-decoration: none;
        }

        .metro:hover {
            background: #0AF;
            text-decoration: none;
        }

        .metro.three-d {
            position: relative;
            box-shadow: 1px 1px #53A7EA,
            2px 2px #53A7EA,
            3px 3px #53A7EA;
            transition: all 0.1s ease-in;
        }

        .metro.three-d:active {
            box-shadow: none;
            top: 3px;
            left: 3px;
        }
    </style>
    <script type="text/javascript">

        $(document).ready(function () {
            jQuery('#fileTreeDemo').gsFileManager({ script: 'gs_connector.php?device=<?=$_GET['device']?>' });
        });
    </script>

</head>

<body>
<div style="height: 16px; line-height: 16px">Last Synced :<?PHP
    include("../functions.php");
    //$ar = explode("|**DATA**|",getFilesList($_GET['device']));
    echo $ar[0];?><a href="/do.php?device=<?= $_GET['device'] ?>&main=filemanager&sub=sync" class="metro">Sync</a><a
        href="file_box.php?device=<?= $_GET['device'] ?>" class="metro">File Box</a></div>
<br/><br/>

<div id="fileTreeDemo"></div>

</body>

</html>