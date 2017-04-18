package entreco.nl.sample.techtalk.detail;

import dagger.Component;
import entreco.nl.sample.techtalk.ApolloModule;

@Component(modules = {TechTalkDetailModule.class, ApolloModule.class})
public interface TechTalkDetailComponent {
    DetailViewModel viewModel();
}
