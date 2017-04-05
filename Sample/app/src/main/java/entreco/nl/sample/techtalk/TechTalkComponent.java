package entreco.nl.sample.techtalk;

import dagger.Component;

@Component(modules = TechTalkModule.class)
interface TechTalkComponent {
    TechTalkViewModel viewModel();
}
