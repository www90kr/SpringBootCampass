/**
 * Created by Administrator on 2018-10-14.
 * Design nas.
 */
function Logic () {

    console.log("%c by.",'background:black; color:white;');
    console.log(
        "     _\n" +
        " ___(_)_     _\n" +
        "/ __| | \\   / |\n" +
        "\\__ \\ |  \\_/  |\n" +
        "|___/_|_|\\_/|_|\n" +
        "\n" +
        "Version : 1.5.0\n" +
        "Author  : SeonBeom Sim\n" +
        "Website : https://github.com/simseonbeom");


    console.log("%c - KindTiger - ",'background:black; color:white;');


    // class delay logic event
    $.fn.queueAddClass = function(className) {
        this.queue('fx', function(next) {
            $(this).addClass(className);
            next();
        });
        return this;
    };
    $.fn.queueRemoveClass = function(className) {
        this.queue('fx', function(next) {
            $(this).removeClass(className);
            next();
        });
        return this;
    };
    $.fn.queuetoggleClass = function(className) {
        this.queue('fx', function(next) {
            $(this).toggleClass(className);
            next();
        });
        return this;
    };


    //hover attr replace event
    $('.hover_img').mouseenter(function(){
        $(this).children('img').attr('src',$(this).children('img').attr('src').replace('.png','_on.png'));
    });
    $('.hover_img').mouseleave(function(){
        $(this).children('img').attr('src',$(this).children('img').attr('src').replace('_on.png','.png'));
    });

    // nav section move event
    $("#nav > li > a, .sectionBtn").click(function () {
        var posY = $($(this).attr("href")).position().top;
        $('#container').stop().animate({"scrollTop": posY}, 1200, 'easeInOutExpo');
        return false;
    });


}