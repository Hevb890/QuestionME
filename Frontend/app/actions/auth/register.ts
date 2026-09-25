
import { redirect } from 'next/navigation'

export async function handleRegister(prevState: any, formData: FormData) {
    const email = formData.get('email') as string
    const password = formData.get('password') as string
    const confirmPassword = formData.get('confirm-password') as string

    if (!email || !password || !confirmPassword) {
        return { error: "All fields are required" }
    }
    
    if (password !== confirmPassword) {
        return { error: "Passwords do not match" }
    }

    try {
        const response = await fetch(`${process.env.SPRING_BOOT_API_URL}/api/v1/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password }),
        })

        const data = await response.json()

        if (!response.ok) {
            return { error: data.message || "Registration failed" }
        }

    }catch (error) {
        return { error: "An error occurred during registration" }
    }

    redirect('/login') // Redirect to login page after successful registration
}