package com.serj.recommend.android.repository

import com.serj.recommend.android.repository.user.LoginRepository
import com.serj.recommend.android.repository.user.ProfileRepository
import com.serj.recommend.android.repository.user.RegistrationRepository

class UserRepository(
    loginRepository: LoginRepository,
    registrationRepository: RegistrationRepository,
    profileRepository: ProfileRepository
) {
}