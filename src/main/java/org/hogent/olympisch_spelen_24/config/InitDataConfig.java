package org.hogent.olympisch_spelen_24.config;

import org.hogent.olympisch_spelen_24.domain.*;
import org.hogent.olympisch_spelen_24.repository.DisciplineRepository;
import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.hogent.olympisch_spelen_24.repository.StadiumRepository;
import org.hogent.olympisch_spelen_24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Component
public class InitDataConfig implements CommandLineRunner {
    private final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public void run(String... args) throws Exception {
        // region Users
        User user = new User(1L, "user", pwEncoder.encode("password"), Role.USER, new HashSet<>());
        User admin = new User(2L, "admin", pwEncoder.encode("admin"), Role.ADMIN, new HashSet<>());

        userRepository.saveAll(List.of(user, admin));
        // endregion

        List<Sport> sports = Stream.of("Golf", "Hockey", "Shooting", "Athletics", "Rowing", "Cycling Road").map(Sport::new).toList();
        sportRepository.saveAll(sports);


        // region Stadiums
        List<Stadium> stadiums = Stream.of(
                "Aquatics Centre",
                "Bercy Arena",
                "Bordeaux Stadium",
                "Champ de Mars Arena",
                "Château de Versailles",
                "Chateauroux Shooting Centre",
                "Eiffel Tower Stadium",
                "Elancourt Hill",
                "Geoffroy-Guichard Stadium",
                "Grand Palais",
                "Hôtel de Ville",
                "Invalides",
                "La Beaujoire Stadium",
                "La Concorde",
                "Le Bourget Sport Climbing Venue",
                "Golf National",
                "Lyon Stadium",
                "Marseille Marina",
                "Marseille Stadium",
                "Nice Stadium",
                "North Paris Arena",
                "Parc des Princes",
                "Paris La Defense Arena",
                "Pierre Mauroy Stadium",
                "Pont Alexandre III",
                "Porte de La Chapelle Arena",
                "Stade Roland-Garros",
                "Saint-Quentin-en-Yvelines BMX Stadium",
                "Saint-Quentin-en-Yvelines Velodrome",
                "South Paris Arena",
                "Stade de France",
                "Teahupo'o, Tahiti",
                "Trocadéro",
                "Vaires-sur-Marne Nautical Stadium",
                "Yves-du-Manoir Stadium").map(Stadium::new).toList();
        stadiumRepository.saveAll(stadiums);
        // endregion

        // region Disciplines
        Stream<Discipline> disciplines = Stream.of(new Discipline("Men's Individual Stroke Play", sports.get(0)), new Discipline("Women's Individual Stroke Play", sports.get(0)));
        Stream<Discipline> athleticDisciplines = Stream.of(
                "Men's 100m",
                "Men's 200m",
                "Men's 400m",
                "Men's 800m",
                "Men's 1500m",
                "Men's 5000m",
                "Men's 10,000m",
                "Men's Marathon",
                "Men's 3000m Steeplechase",
                "Men's 110m Hurdles",
                "Men's 400m Hurdles",
                "Men's High Jump",
                "Men's Pole Vault",
                "Men's Long Jump",
                "Men's Triple Jump",
                "Men's Shot Put",
                "Men's Discus Throw",
                "Men's Hammer Throw",
                "Men's Javelin Throw",
                "Men's Decathlon",
                "Men's 20km Race Walk",
                "Men's 4 x 100m Relay",
                "Men's 4 x 400m Relay",
                "Women's 100m",
                "Women's 200m",
                "Women's 400m",
                "Women's 800m",
                "Women's 1500m",
                "Women's 5000m",
                "Women's 10,000m",
                "Women's Marathon",
                "Women's 3000m Steeplechase",
                "Women's 100m Hurdles",
                "Women's 400m Hurdles",
                "Women's High Jump",
                "Women's Pole Vault",
                "Women's Long Jump",
                "Women's Triple Jump",
                "Women's Shot Put",
                "Women's Discus Throw",
                "Women's Hammer Throw",
                "Women's Javelin Throw",
                "Women's Heptathlon",
                "Women's 20km Race Walk",
                "Women's 4 x 100m Relay",
                "Women's 4 x 400m Relay",
                "4 x 400m Relay Mixed",
                "Marathon Race Walk Mixed Relay"
        ).map(d -> new Discipline(d, sports.get(3)));
        disciplineRepository.saveAll(Stream.concat(disciplines, athleticDisciplines).toList());
        // endregion
    }
}
