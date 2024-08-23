import React from 'react'
import HeroSection from '../components/HeroSection'
import Navbar from '../components/Navbar'
import Footer from '../components/Footer'

export default function Landing() {
  
  return (
    <>
     <Navbar/>
     <div className="max-w-7xl mx-auto pt-20 px-6">
     <HeroSection/>
     <Footer/>
     </div>
     
    </>
  )}
