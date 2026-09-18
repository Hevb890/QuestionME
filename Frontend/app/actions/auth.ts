"user server"

import { cookies } from 'next/headers'

export async function handleLogin(prevState: any, formData: FormData) {
    const email = formData.get('email') as string
    const password = formData.get('password') as string

    const response = await fetch(`${process.env.SPRING_BOOT_API_URL}/api/v1/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
    })

    if (!response.ok) return {error: "Invalid Credentials"}
    const data = await response.json()

    const cookieStore = await cookies()

    cookieStore.set("auth_token", data.accessToken, {
        httpOnly: true,
        secure: true,
        sameSite: 'strict',
        path: '/',
    })

    return {success: true}
}