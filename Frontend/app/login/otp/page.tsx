"use client"

import Image from 'next/image'
import Link from 'next/link'
import { useActionState, useRef, useState } from 'react'
import { handleVerifyOtp } from '@/app/actions/auth'

export default function OtpPage() {
  const [state, formAction, isPending] = useActionState(handleVerifyOtp, null)
  const [otpValues, setOtpValues] = useState<string[]>(Array(6).fill(''))
  const inputRefs = useRef<HTMLInputElement[]>([])

  // Manage focusing behavior across the 8 boxes
  const handleChange = (value: string, index: number) => {
    if (isNaN(Number(value))) return // Allow numeric digits only

    const newOtpValues = [...otpValues]
    newOtpValues[index] = value.substring(value.length - 1) // Keep only the last character entered
    setOtpValues(newOtpValues)

    // Move focus forward if value is filled
    if (value && index < 5) {
      inputRefs.current[index + 1]?.focus()
    }
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>, index: number) => {
    // Move focus backward on backspace if field is currently empty
    if (e.key === 'Backspace' && !otpValues[index] && index > 0) {
      inputRefs.current[index - 1]?.focus()
    }
  }

  const handlePaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
    e.preventDefault()
    const pastedData = e.clipboardData.getData('text').replace(/\D/g, '').substring(0, 6)
    
    const newOtpValues = Array(6).fill('')
    for (let i = 0; i < pastedData.length; i++) {
      newOtpValues[i] = pastedData[i]
    }
    setOtpValues(newOtpValues)

    // Shift focus to the last filled box or final box
    const focusIndex = pastedData.length < 6 ? pastedData.length : 5
    inputRefs.current[focusIndex]?.focus()
  }

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
                          OTP Authentication
                      </h1>
                      <p className="text-sm text-gray-500 dark:text-gray-400">
                          Please enter the 6-digit verification code sent to your email.
                      </p>
                  </div>
                  <form className="space-y-4 md:space-y-6" action={formAction}>
                      
                      {/* 8-Box Layout Row */}
                      <div className="flex justify-between gap-2">
                        {otpValues.map((digit, index) => (
                          <input
                            key={index}
                            ref={(el) => { inputRefs.current[index] = el! }}
                            type="text"
                            inputMode="numeric"
                            maxLength={1}
                            value={digit}
                            onChange={(e) => handleChange(e.target.value, index)}
                            onKeyDown={(e) => handleKeyDown(e, index)}
                            onPaste={handlePaste}
                            className="w-12 h-14 bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 text-center text-xl font-bold dark:bg-gray-700 dark:border-gray-600 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                            required
                          />
                        ))}
                      </div>

                      {/* Hidden string input mapping the total values array natively into FormData for your server action */}
                      <input type="hidden" name="otp" value={otpValues.join('')} />
                      
                      {state?.error && <p className="text-red-500 text-sm text-center">{state.error}</p>}

                      <button 
                          type="submit" 
                          disabled={isPending || otpValues.join('').length < 8} 
                          className="w-full text-white bg-primary-600 hover:bg-primary-300 hover:text-black focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        {isPending ? 'Verifying...' : 'Verify Code'}
                      </button>

                      <p className="text-sm text-center font-light text-gray-500 dark:text-gray-400">
                          Didn’t receive the code?{' '}
                          <button 
                              type="button"
                              className="font-medium text-primary-600 hover:underline dark:text-primary-500 bg-transparent border-none p-0 cursor-pointer"
                              onClick={() => {/* Implement resend trigger here */}}
                          >
                              Resend
                          </button>
                      </p>
                  </form>
              </div>
          </div>
      </div>
    </section>
  )
}
