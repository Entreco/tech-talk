package entreco.nl.sample.techtalk.master;

import dagger.Component;

@Component(modules = TechTalkModule.class)
interface TechTalkComponent {
    MasterViewModel viewModel();
}
