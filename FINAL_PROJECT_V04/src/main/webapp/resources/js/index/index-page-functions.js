$(document).ready(function() {

	var menu = $('.navbar');
	var origOffsetY = menu.offset().top;
	console.log(origOffsetY);
	
	$(window).on('scroll', function() {
		
		console.log($(window).scrollTop());
		console.log(origOffsetY);
		
		if ($(window).scrollTop() >= origOffsetY) {
			
			$('.navbar').addClass('navbar-wrap');
			
		} else {
			
			$('.navbar').removeClass('navbar-wrap');
		}
	});
})