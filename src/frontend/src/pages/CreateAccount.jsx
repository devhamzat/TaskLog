import React, { useState } from "react";
import axios from "axios";
import { NotebookTabs } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
export default function CreateAccount() {

  let navigate = useNavigate();
  const [user, setUser] = useState({
    userName: "",
    email: "",
    password: "",
   
  });

  const { userName,email, password } = user;

  const onInputChange = (event) => {
    const{name,value} = event.target;
    setUser({ ...user, [name]:value, });
  };
  const onSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:3009/tasklogue/user/create_account",
        user
      );
      console.log(response);
      navigate("/");
    } catch (error) {
      console.log(error.response);
    }
  };
  return (
    <section className="bg-white-50 dark:bg-white-100">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <Link
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
          to="/"
        >
          {/*change to image when you get logo  */}
          <NotebookTabs />
          <span className="text-xl tracking-tight">TaskLogue</span>
        </Link>
        <div className="w-full rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0  dark:border-gray-700 bg-transparent backdrop-blur">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white text-center">
              Create an account
            </h1>
            <form
              className="space-y-4 md:space-y-6"
              onSubmit={(event) => onSubmit(event)}
            >
              <div>
                <label
                  htmlFor="userName"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  UserName
                </label>
                <input
                  type="text"
                  value={userName}
                  name="userName"
                  id="userName"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="JohnDoe"
                  required=""
                  onChange={(event) => onInputChange(event)}
                />
              </div>
              <div>
                <label
                  htmlFor="email"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Email
                </label>
                <input
                  type="email"
                  value={email}
                  name="email"
                  id="email"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="name@company.com"
                  required=""
                  onChange={(event) => onInputChange(event)}
                />
              </div>
              <div>
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Password
                </label>
                <input
                  type="password"
                  value={password}
                  name="password"
                  id="password"
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required=""
                  onChange={(event) => onInputChange(event)}
                />
              </div>
              <div>
                <label
                  htmlFor="confirm-password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Confirm password
                </label>
                <input
                  type="password"
                  name="confirm-password"
                  id="confirm-password"
                  placeholder="••••••••"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  required=""
                  onChange={(event) => onInputChange(event)}
                />
              </div>
              <div className="flex items-start">
                <div className="flex items-center h-5">
                  <input
                    id="terms"
                    aria-describedby="terms"
                    type="checkbox"
                    className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                    required=""
                  />
                </div>
                <div className="ml-3 text-sm">
                  <label
                    htmlFor="terms"
                    className="font-light text-gray-500 dark:text-gray-300"
                  >
                    I accept the{" "}
                    <a
                      className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                      href="#"
                    >
                      Terms and Conditions
                    </a>
                  </label>
                </div>
              </div>
              <button
                type="submit"
                className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
              >
                Create an account
              </button>
              <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                Already have an account?{" "}
                <a
                  href="#"
                  className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                >
                  Login here
                </a>
              </p>
            </form>
          </div>
        </div>
      </div>
    </section>
  );
}

// import React, { useState } from "react";
// import axios from "axios";
// import { NotebookTabs } from "lucide-react";
// import { Link, useNavigate } from "react-router-dom";

// export default function CreateAccount() {
//   let navigate = useNavigate();
//   const [user, setUser] = useState({
//     userName: "",
//     email: "",
//     password: "",
//     confirmPassword: "",
//   });

//   const [errors, setErrors] = useState({});

//   const { userName, email, password, confirmPassword } = user;

//   const onInputChange = (event) => {
//     const { name, value } = event.target;
//     setUser({ ...user, [name]: value });
//   };

//   const validateForm = (data) => {
//     const errors = {};

//     if (!data.userName.trim()) {
//       errors.userName = "Username is required";
//     } else if (data.userName.length < 4) {
//       errors.userName = "Username must be at least 4 characters long";
//     }

//     if (!data.email.trim()) {
//       errors.email = "Email is required";
//     } else if (!/\S+@\S+\.\S+/.test(data.email)) {
//       errors.email = "Email is invalid";
//     }

//     if (!data.password) {
//       errors.password = "Password is required";
//     } else if (data.password.length < 8) {
//       errors.password = "Password must be at least 8 characters long";
//     }

//     if (data.confirmPassword !== data.password) {
//       errors.confirmPassword = "Passwords do not match";
//     }

//     return errors;
//   };

//   const onSubmit = async (event) => {
//     event.preventDefault();

//     const newErrors = validateForm(user);
//     setErrors(newErrors);

//     if (Object.keys(newErrors).length === 0) {
//       try {
//         const response = await axios.post(
//           "http://localhost:3009/tasklogue/user/create_account",
//           user
//         );
//         console.log(response);
//         navigate("/");
//       } catch (error) {
//         console.log(error.response);
//       }
//     } else {
//       console.log("Form submission failed due to validation errors.");
//     }
//   };

//   return (
//     <section className="bg-white-50 dark:bg-white-100">
//       <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
//         <Link
//           className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
//           to="/"
//         >
//           <NotebookTabs />
//           <span className="text-xl tracking-tight">TaskLogue</span>
//         </Link>
//         <div className="w-full rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0  dark:border-gray-700 bg-transparent backdrop-blur">
//           <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
//             <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white text-center">
//               Create an account
//             </h1>
//             <form
//               className="space-y-4 md:space-y-6"
//               onSubmit={(event) => onSubmit(event)}
//             >
//               <div>
//                 <label
//                   htmlFor="userName"
//                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
//                 >
//                   UserName
//                 </label>
//                 <input
//                   type="text"
//                   value={userName}
//                   name="userName"
//                   id="userName"
//                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
//                   placeholder="JohnDoe"
//                   required=""
//                   onChange={(event) => onInputChange(event)}
//                 />
//                 {errors.userName && (
//                   <span className="text-red-500 text-sm">
//                     {errors.userName}
//                   </span>
//                 )}
//               </div>
//               <div>
//                 <label
//                   htmlFor="email"
//                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
//                 >
//                   Email
//                 </label>
//                 <input
//                   type="email"
//                   value={email}
//                   name="email"
//                   id="email"
//                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
//                   placeholder="name@company.com"
//                   required=""
//                   onChange={(event) => onInputChange(event)}
//                 />
//                 {errors.email && (
//                   <span className="text-red-500 text-sm">{errors.email}</span>
//                 )}
//               </div>
//               <div>
//                 <label
//                   htmlFor="password"
//                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
//                 >
//                   Password
//                 </label>
//                 <input
//                   type="password"
//                   value={password}
//                   name="password"
//                   id="password"
//                   placeholder="••••••••"
//                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
//                   required=""
//                   onChange={(event) => onInputChange(event)}
//                 />
//                 {errors.password && (
//                   <span className="text-red-500 text-sm">
//                     {errors.password}
//                   </span>
//                 )}
//               </div>
//               <div>
//                 <label
//                   htmlFor="confirmPassword"
//                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
//                 >
//                   Confirm password
//                 </label>
//                 <input
//                   type="password"
//                   name="confirmPassword"
//                   id="confirmPassword"
//                   placeholder="••••••••"
//                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
//                   required=""
//                   onChange={(event) => onInputChange(event)}
//                 />
//                 {errors.confirmPassword && (
//                   <span className="text-red-500 text-sm">
//                     {errors.confirmPassword}
//                   </span>
//                 )}
//               </div>
//               <div className="flex items-start">
//                 <div className="flex items-center h-5">
//                   <input
//                     id="terms"
//                     aria-describedby="terms"
//                     type="checkbox"
//                     className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
//                     required=""
//                   />
//                 </div>
//                 <div className="ml-3 text-sm">
//                   <label
//                     htmlFor="terms"
//                     className="font-light text-gray-500 dark:text-gray-300"
//                   >
//                     I accept the{" "}
//                     <a
//                       className="font-medium text-primary-600 hover:underline dark:text-primary-500"
//                       href="#"
//                     >
//                       Terms and Conditions
//                     </a>
//                   </label>
//                 </div>
//               </div>
//               <button
//                 type="submit"
//                 className="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
//               >
//                 Create an account
//               </button>
//               <p className="text-sm font-light text-gray-500 dark:text-gray-400">
//                 Already have an account?{" "}
//                 <a
//                   href="#"
//                   className="font-medium text-primary-600 hover:underline dark:text-primary-500"
//                 >
//                   Login here
//                 </a>
//               </p>
//             </form>
//           </div>
//         </div>
//       </div>
//     </section>
//   );
// }
