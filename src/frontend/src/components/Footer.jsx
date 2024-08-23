import React from "react";
import { Linkedin,Twitter } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="mt-20 border-t py-10 border-neutral-700">
      <div className="grid grid-cols-2 lg:grid-cols-3 gap-4">
        <div>
          <h3 className="text-md font-semibold mb-4">
            Contact
          </h3>
          <ul>
            <li>
              <a href="#"><Linkedin /></a>
              <a href="#"><Twitter /></a>
            </li>
          </ul>
        </div>
      </div>
    </footer>
  );
}
