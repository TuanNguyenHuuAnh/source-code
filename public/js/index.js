import { Fancybox } from "@fancyapps/ui"
import Swiper, { Pagination } from 'swiper'
import './main'
import './popup'

Swiper.use( [ Pagination ] )
Fancybox.bind( '[data-fancybox="gallery"]' )
const countDownEle = document.getElementById( "countdown-block" )
if ( countDownEle ) {
	const countDownDate = new Date( "Jan 5, 2024 15:37:25" ).getTime()

	// Update the count down every 1 second
	const x = setInterval( function() {
		// Get today's date and time
		const now = new Date().getTime()
  
		// Find the distance between now and the count down date
		const distance = countDownDate - now
  
		// Time calculations for days, hours, minutes and seconds
		const days = Math.floor( distance / ( 1000 * 60 * 60 * 24 ) )
		const hours = Math.floor( ( distance % ( 1000 * 60 * 60 * 24 ) ) / ( 1000 * 60 * 60 ) )
		const minutes = Math.floor( ( distance % ( 1000 * 60 * 60 ) ) / ( 1000 * 60 ) )
		const seconds = Math.floor( ( distance % ( 1000 * 60 ) ) / 1000 )
  
		// Display the result in the element with id="demo"
		// document.getElementById( "demo" ).innerHTML = days + "d " + hours + "h "
		// + minutes + "m " + seconds + "s "
		const countWrap = countDownEle.getElementsByClassName( 'countdown-date-wrap' )[ 0 ]
		countWrap.innerHTML = '<div class="countdown-date-item"><span>' + days + '</span><label>Ngày</label></div><div class="countdown-date-item"><span>' + hours + '</span><label>Giờ</label></div><div class="countdown-date-item"><span>' + minutes + '</span><label>Phút</label></div><div class="countdown-date-item"><span>' + seconds + '</span><label>Giây</label></div>'
  
		// If the count down is finished, write some text
		if ( distance < 0 ) {
			clearInterval( x )
			countWrap.innerHTML = '<div class="countdown-date-item"><span>00</span><label>Ngày</label></div><div class="countdown-date-item"><span>00</span><label>Giờ</label></div><div class="countdown-date-item"><span>00</span><label>Phút</label></div><div class="countdown-date-item"><span>00</span><label>Giây</label></div>'
		}
	}, 1000 )
}
new Swiper( ".mySwiper", {
	slidesPerView: 3,
	breakpoints: {
		640: {
			slidesPerView: 3
			// spaceBetween: 20,
		},
		768: {
			slidesPerView: 3
			// spaceBetween: 40,
		},
		1024: {
			slidesPerView: 5
		}
	}
} )

new Swiper( ".homeswiper", {
	loop: true,
	pagination: {
		el: ".swiper-pagination"
	}
} )
new Swiper( ".products-slider", {
	loop: true,
	slidesPerView: 2,
	spaceBetween: 10,
	// centeredSlides: true,
	breakpoints: {
		640: {
			slidesPerView: 2
			// spaceBetween: 10,
		},
		768: {
			slidesPerView: 2
			// spaceBetween: 40,
		},
		1024: {
			slidesPerView: 6,
			noSwiping: true,
			spaceBetween: 20
		}
	}
} )