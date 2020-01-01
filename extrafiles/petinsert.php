<?php

$servername = "localhost";
$username = "petfind4_app";
$password = "Pass123#";
$dbname = "petfind4_petfinder";

//open connection to mysql db
$connection = mysqli_connect($servername,$username,$password,$dbname) or die("Error " . mysqli_error($connection));

if(isset($_POST['species'])) {
    $species = $_POST['species'];
    $breed = $_POST['breed'];
    $size = $_POST['size'];
    $found_date = $_POST['found_date'];
    $color = $_POST['color'];
    $is_urgent = $_POST['is_urgent'];
    $location = $_POST['location'];
    $title = $_POST['title'];
    $description = $_POST['description'];
    $has_permanent_home = $_POST['has_permanent_home'];

    $sql_query = "INSERT INTO pet (species, breed, size, found_date, color, is_urgent, post_date, title, description, has_permanent_home, location)
                    VALUES ('$species', '$breed', '$size', STR_TO_DATE('$found_date', '%m/%d/%Y'), '$color', '$is_urgent', now(), '$title', '$description', '$has_permanent_home', '$location')";

    echo "Constructed query: " . $sql_query . "<br />\n\n";
    $result = mysqli_query($connection, $sql_query) or die("Error in inserting " . mysqli_error($connection));
    $last_id = mysqli_insert_id($connection);

    $encoded_string = $_POST["encoded_string"];

    //close the db connection
    mysqli_close($connection);

    $path = "pet_images/$last_id/";

    if (!file_exists($path)) {
        if (!mkdir($path, 0777, true)) {
            echo "Could not create directory.";
        } else {
            $imageData = base64_decode($encoded_string);
            $source = imagecreatefromstring($imageData);
            $imageSave = imagejpeg($source, $path . "main.jpg",100);
            imagedestroy($source);
            //base64_to_jpeg($encoded_string, $path . "main.jpg");
        }
    }
}

function base64_to_jpeg($base64_string, $output_file) {
    // open the output file for writing
    $ifp = fopen($output_file, 'wb');

    // split the string on commas
    // $data[ 0 ] == "data:image/png;base64"
    // $data[ 1 ] == <actual base64 string>
    $data = explode(',', $base64_string);

    // we could add validation here with ensuring count( $data ) > 1
    fwrite($ifp, base64_decode( $data[ 1 ]));

    // clean up the file resource
    fclose($ifp);

    return $output_file;
}