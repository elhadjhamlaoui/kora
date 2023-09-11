package com.app_republic.shoot.model.PlayersResponse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StatisticsItem implements Parcelable {
	private Fouls fouls;
	private Cards cards;
	private Dribbles dribbles;
	private Substitutes substitutes;
	private Penalty penalty;
	private League league;
	private Team team;
	private Duels duels;
	private Passes passes;
	private Games games;
	private Tackles tackles;
	private Shots shots;
	private Goals goals;

	protected StatisticsItem(Parcel in) {
		fouls = in.readParcelable(Fouls.class.getClassLoader());
		cards = in.readParcelable(Cards.class.getClassLoader());
		dribbles = in.readParcelable(Dribbles.class.getClassLoader());
		substitutes = in.readParcelable(Substitutes.class.getClassLoader());
		penalty = in.readParcelable(Penalty.class.getClassLoader());
		league = in.readParcelable(League.class.getClassLoader());
		team = in.readParcelable(Team.class.getClassLoader());
		duels = in.readParcelable(Duels.class.getClassLoader());
		passes = in.readParcelable(Passes.class.getClassLoader());
		games = in.readParcelable(Games.class.getClassLoader());
		tackles = in.readParcelable(Tackles.class.getClassLoader());
		shots = in.readParcelable(Shots.class.getClassLoader());
		goals = in.readParcelable(Goals.class.getClassLoader());
	}

	public static final Creator<StatisticsItem> CREATOR = new Creator<StatisticsItem>() {
		@Override
		public StatisticsItem createFromParcel(Parcel in) {
			return new StatisticsItem(in);
		}

		@Override
		public StatisticsItem[] newArray(int size) {
			return new StatisticsItem[size];
		}
	};

	public void setFouls(Fouls fouls){
		this.fouls = fouls;
	}

	public Fouls getFouls(){
		return fouls;
	}

	public void setCards(Cards cards){
		this.cards = cards;
	}

	public Cards getCards(){
		return cards;
	}

	public void setDribbles(Dribbles dribbles){
		this.dribbles = dribbles;
	}

	public Dribbles getDribbles(){
		return dribbles;
	}

	public void setSubstitutes(Substitutes substitutes){
		this.substitutes = substitutes;
	}

	public Substitutes getSubstitutes(){
		return substitutes;
	}

	public void setPenalty(Penalty penalty){
		this.penalty = penalty;
	}

	public Penalty getPenalty(){
		return penalty;
	}

	public void setLeague(League league){
		this.league = league;
	}

	public League getLeague(){
		return league;
	}

	public void setTeam(Team team){
		this.team = team;
	}

	public Team getTeam(){
		return team;
	}

	public void setDuels(Duels duels){
		this.duels = duels;
	}

	public Duels getDuels(){
		return duels;
	}

	public void setPasses(Passes passes){
		this.passes = passes;
	}

	public Passes getPasses(){
		return passes;
	}

	public void setGames(Games games){
		this.games = games;
	}

	public Games getGames(){
		return games;
	}

	public void setTackles(Tackles tackles){
		this.tackles = tackles;
	}

	public Tackles getTackles(){
		return tackles;
	}

	public void setShots(Shots shots){
		this.shots = shots;
	}

	public Shots getShots(){
		return shots;
	}

	public void setGoals(Goals goals){
		this.goals = goals;
	}

	public Goals getGoals(){
		return goals;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel parcel, int i) {
		parcel.writeParcelable(fouls, i);
		parcel.writeParcelable(cards, i);
		parcel.writeParcelable(dribbles, i);
		parcel.writeParcelable(substitutes, i);
		parcel.writeParcelable(penalty, i);
		parcel.writeParcelable(league, i);
		parcel.writeParcelable(team, i);
		parcel.writeParcelable(duels, i);
		parcel.writeParcelable(passes, i);
		parcel.writeParcelable(games, i);
		parcel.writeParcelable(tackles, i);
		parcel.writeParcelable(shots, i);
		parcel.writeParcelable(goals, i);
	}
}
