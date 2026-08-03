import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

export type Language = 'en' | 'fr' | 'ar';

interface Translation {
  title: string;
  subtitle: string;
  emailLabel: string;
  emailPlaceholder: string;
  passwordLabel: string;
  passwordPlaceholder: string;
  rememberMe: string;
  forgotPassword: string;
  signInBtn: string;
  orContinueWith: string;
  googleBtn: string;
  noAccount: string;
  signUp: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  currentLang: Language = 'en';
  isRtl: boolean = false;

  translations: Record<Language, Translation> = {
    en: {
      title: 'Welcome back',
      subtitle: 'Enter your credentials to access your workspace',
      emailLabel: 'Email address',
      emailPlaceholder: 'name@company.com',
      passwordLabel: 'Password',
      passwordPlaceholder: '••••••••',
      rememberMe: 'Remember for 30 days',
      forgotPassword: 'Forgot password?',
      signInBtn: 'Sign in',
      orContinueWith: 'Or continue with',
      googleBtn: 'Sign in with Google',
      noAccount: "Don't have an account?",
      signUp: 'Sign up'
    },
    fr: {
      title: 'Bon retour parmi nous',
      subtitle: 'Entrez vos identifiants pour accéder à votre espace',
      emailLabel: 'Adresse e-mail',
      emailPlaceholder: 'nom@entreprise.com',
      passwordLabel: 'Mot de passe',
      passwordPlaceholder: '••••••••',
      rememberMe: 'Se souvenir de moi 30 jours',
      forgotPassword: 'Mot de passe oublié ?',
      signInBtn: 'Se connecter',
      orContinueWith: 'Ou continuer avec',
      googleBtn: 'Se connecter avec Google',
      noAccount: "Vous n'avez pas de compte ?",
      signUp: "S'inscrire"
    },
    ar: {
      title: 'مرحباً بعودتك',
      subtitle: 'أدخل بيانات الاعتماد الخاصة بك للوصول إلى مساحة العمل',
      emailLabel: 'البريد الإلكتروني',
      emailPlaceholder: 'name@company.com',
      passwordLabel: 'كلمة المرور',
      passwordPlaceholder: '••••••••',
      rememberMe: 'تذكرني لمدة 30 يومًا',
      forgotPassword: 'نسيت كلمة المرور؟',
      signInBtn: 'تسجيل الدخول',
      orContinueWith: 'أو المتابعة باستخدام',
      googleBtn: 'تسجيل الدخول باستخدام Google',
      noAccount: 'ليس لديك حساب؟',
      signUp: 'إنشاء حساب'
    }
  };

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      rememberMe: [false]
    });
  }

  get t(): Translation {
    return this.translations[this.currentLang];
  }

  changeLanguage(lang: Language): void {
    this.currentLang = lang;
    this.isRtl = lang === 'ar';
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      console.log('Form values:', this.loginForm.value);
      // Logique d'authentification avec votre backend
    }
  }

  loginWithGoogle(): void {
    console.log('Initiating Google OAuth login...');
    // Logique d'authentification Google
  }
}
