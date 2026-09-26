"use server"

import { redirect } from "next/navigation"


export async function requestOTP(prevState: any, formData: FormData){
    const email = formData.get("email") as string

    if (!email) {
        return {error: "Email cannot be empty"}
    }

    try {
        const response = await fetch(`${process.env.SPRING_BOOT_API_URL}/api/v1/auth/forget-password`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({email}),
        })
        const data = await response.json()

        if (!response.ok) {
            return {error: data.message || "No Email Found"}
        }
    }catch (error) {
        return { error: "An error occured during sending email."}
    }

    redirect('/login/otp');
}