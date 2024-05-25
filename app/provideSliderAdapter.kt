@Provides
fun provideSliderAdapter(viewPager2: ViewPager2): SliderAdapter {
    return SliderAdapter(ArrayList(), viewPager2)
}