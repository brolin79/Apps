// MEDIA
var my_media = null;
var mediaTimer = null;
var dur = -1;  // duration of media (song)
var is_paused = false; // need to know when paused or not

function onSuccess() {
    setAudioPosition(dur);
    clearInterval(mediaTimer);
    mediaTimer = null;
    my_media = null;
    is_paused = false;
    dur = -1;
}
function onError(error) {
    alert('code: '    + error.code    + '\n' + 
            'message: ' + error.message + '\n');
    clearInterval(mediaTimer);
    mediaTimer = null;
    my_media = null;
    is_paused = false;
    setAudioPosition("0");
}
function playAudio(src) {
    if (my_media === null) {         
        // Create Media object from src
        my_media = new Media(src, onSuccess, onError);       
        my_media.play();
    } else {
        if (is_paused) {
            // to resume where paused in song: call .play()
            is_paused = false;
            my_media.play();
        }
    }
}
function stopAudio() {
    if (my_media) {
        // A successful .stop() will call .release()
        my_media.stop();
        my_media = null;
    }
    if (mediaTimer) {
        clearInterval(mediaTimer);
        mediaTimer = null;
    }
    is_paused = false;
    dur = 0;
}

$(document).ready(function() {       
    $("#playaudio").click(function() {         
        var src = '/android_asset/www/Himno.mp3';          
        playAudio(src);
    });   
    $("#stopaudio").click(function() {
        stopAudio();
    });  
});