"use client"

import Image from 'next/image'
import Link from 'next/link'
import { useActionState } from 'react'
import { requestOTP } from '@/app/actions/auth' // Adjust this path to match where your actions live

export default function ForgotPasswordPage() {
  const [state, formAction, isPending] = useActionState(requestOTP, null)

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
          <Link href="/" className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
              <Image src="/question-mark.png" alt="logo" width={32} height={32} className="w-8 h-8 mr-2" />
              QuestionME  
          </Link>
          <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-xl xl:p-0 dark:bg-gray-800 dark:border-gray-700">
              <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                  <div className="space-y-2 text-center">
                      <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                          Forgot Password?
                      </h1>
                      <p className="text-sm text-gray-500 dark:text-gray-400">
                          Enter your email address and we'll send you a 6-digit verification code.
                      </p>
                  </div>
                  <form className="space-y-4 md:space-y-6" action={formAction}>
                      
                      <div>
                          <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                              Your Email
                          </label>
                          <input 
                              type="email" 
                              name="email" 
                              id="email" 
                              className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                              placeholder="name@company.com" 
                              required 
                              disabled={isPending}
                          />
                      </div>
                      
                      {state?.error && <p className="text-red-500 text-sm text-center">{state.error}</p>}

                      <button 
                          type="submit" 
                          disabled={isPending} 
                          className="w-full text-white bg-primary-600 hover:bg-primary-300 hover:text-black focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        {isPending ? 'Sending Code...' : 'Send Verification Code'}
                      </button>

                      <p className="text-sm text-center font-light text-gray-500 dark:text-gray-400">
                          Remember your password?{' '}
                          <Link href="/login" className="font-medium text-primary-600 hover:underline dark:text-primary-500">
                              Sign In
                          </Link>
                      </p>
                  </form>
              </div>
          </div>
      </div>
    </section>
  )
}
