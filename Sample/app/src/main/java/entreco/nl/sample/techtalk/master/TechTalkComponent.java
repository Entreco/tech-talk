package entreco.nl.sample.techtalk.master;

import dagger.Component;
import entreco.nl.sample.techtalk.ApolloModule;

@Component(modules = {TechTalkModule.class, ApolloModule.class})
interface TechTalkComponent {
    MasterViewModel viewModel();
}
