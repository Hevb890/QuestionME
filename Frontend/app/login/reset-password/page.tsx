"use client"

import Image from 'next/image'
import Link from 'next/link'
import { useActionState } from 'react'
import { handleResetPassword } from '@/app/actions/auth' // Ensure this points to your modular server action

export default function ResetPasswordPage() {
  const [state, formAction, isPending] = useActionState(handleResetPassword, null)

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
          <Link href="/" className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white">
              <Image src="/question-mark.png" alt="logo" width={32} height={32} className="w-8 h-8 mr-2" />
              QuestionME  
          </Link>
          <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
              <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                  <div className="space-y-2 text-center">
                      <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                          Update Password
                      </h1>
                      <p className="text-sm text-gray-500 dark:text-gray-400">
                          Create a strong new password for your account.
                      </p>
                  </div>
                  <form className="space-y-4 md:space-y-6" action={formAction}>
                      <div>
                          <label htmlFor="newPassword" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                              New Password
                          </label>
                          <input 
                              type="password" 
                              name="newPassword" 
                              id="newPassword" 
                              placeholder="••••••••" 
                              className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                              required
                          />
                      </div>
                      <div>
                          <label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                              Confirm New Password
                          </label>
                          <input 
                              type="password" 
                              name="confirmPassword" 
                              id="confirmPassword" 
                              placeholder="••••••••" 
                              className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                              required
                          />
                      </div>

                      {state?.error && <p className="text-red-500 text-sm text-center">{state.error}</p>}
                      {state?.success && <p className="text-green-500 text-sm text-center">{state.success}</p>}

                      <button 
                          type="submit" 
                          disabled={isPending} 
                          className="w-full text-white bg-primary-600 hover:bg-primary-300 hover:text-black focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center disabled:opacity-50"
                      >
                        {isPending ? 'Updating...' : 'Reset Password'}
                      </button>

                      <p className="text-sm text-center font-light text-gray-500 dark:text-gray-400">
                          Remember your password?{' '}
                          <Link 
                            href="/login" 
                            className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                          >
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
