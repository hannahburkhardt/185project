/**
 * Created by Hannah on 5/24/14.
 */
var main = function () {
    var readSeq = function () {
        var seq = "";
        var input_seq = $(".inseq").val();
        if (input_seq === "") return;

        seq = seq + "{nodes:[";
        for (i in input_seq) {
            var l;
            switch (input_seq[i]) {
                case 'A':
                case 'a':
                    l = 'a';
                    break;
                case 'C':
                case 'c':
                    l = 'c';
                    break;
                case 'G':
                case 'g':
                    l = 'g';
                    break;
                case 'U':
                case 'u':
                    l = 'u';
                    break;
                default:
                    l = 'a';
                    break;
            }
            seq = seq + ('{"base": "' + l + '"},')
        }
        seq = seq + ("]}");

        console.log(seq);
    };


    $(".btn").on("click", function (event) {
        readSeq();
    });

    $(".btn").on("keypress", function (event) {
        if (event.keyCode === 13) //enter
            readSeq();
    });

};
$("document").ready(main());