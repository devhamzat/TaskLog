import React from 'react'
import {  NotebookTabs } from 'lucide-react';
import { navItems } from '../constants';
import { Menu,X } from 'lucide-react';
import { useState } from 'react';
import { Link } from 'react-router-dom';

export default function Navbar() {
    const [mobileDrawOpen,setMobileDrawOpen] = useState(false)
    const toggleNav = ()=>{
        setMobileDrawOpen(!mobileDrawOpen);
    };
  return (
    <nav className="sticky top-0 z-50 py-3 backdrop-blur-lg border-b border-neutral-700/80">
        <div className="container px-4 mx-auto relative text-sm">
            <div className="flex justify-between items-center">
                <div className="flex items-center flex-shrink text-gray-400">
                    {/* //change to an image when you have a logo */}
                <NotebookTabs /><span className='text-xl tracking-tight'>TaskLogue</span>
                </div>
                <ul className='hidden lg:flex ml-14 space-x-12'>
                    {navItems.map((item,index)=>(
                        <li key={index}>
                            <a href={item.href}>{item.label}</a>
                        </li>
                    ))}
                </ul>
                <div className="hidden lg:flex justify-center space-x-12 items-center">
                    <a href="#" className=' py-2 px-3 border-none'> Sign in</a>
                    <Link className='bg-gradient-to-r from-gray-300 to-gray-100 py-2 px-3 border rounded-md text-black ' to="/createAccount">
                        Create Account
                    </Link>
                </div>
                <div className="lg:hidden md:flex flex-col justify-end">
                    <button onClick={toggleNav}>
                        {mobileDrawOpen?<X/>:<Menu/>}
                    </button>
                </div>
            </div>
            {mobileDrawOpen&&(
                <div className="fixed-right-0 z-20 bg-neutral-900 w-full p-12 flexc-col justify-center item-center lg:hidden">
                    <ul>
                        {navItems.map((item,index)=>(
                            <li Key={index} className='py-4 '>
                                <a href={item.href}>{item.label}</a>
                            </li>
                        ))}
                    </ul>
                    <div className="flex py-4">
                    <Link className='bg-gradient-to-r from-gray-300 to-gray-100 py-2 px-3 border rounded-md text-black ' to="/createAccount">
                        Create Account
                    </Link>
                    </div>
                </div>
            )}
        </div>
    </nav>
  )
}
