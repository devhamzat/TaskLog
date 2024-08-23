import React from 'react';
import { Link } from 'react-router-dom';

const HeroSection = () => {
  return (
    <>
       <div className="flex flex-col items-center mt-6 lg:mt-20">
        <h1 className="text-4xl sm:text-6xl lg:text-7xl text-center tracking-wide">
             TaskLogue log your 
             <span className='bg-gradient-to-l from-gray-500 to-gray-300 text-transparent bg-clip-text' >{" "}tasks</span>
        </h1>
        <p className='mt-5 text-lg text-center text-neutral-500 maxw-4xl'>
            Manage and log your tasks with TaskLogue. Get started today and ease your hefty tasks 
        </p>
        <div className="flex justify-center my-10">
        <Link className='bg-gradient-to-r from-gray-300 to-gray-100 py-3 px-4  mx-3border rounded-md text-black ' to="/createAccount">
                        Get Started
                    </Link>
        </div>
       </div>
    </>
  );
};

export default HeroSection;